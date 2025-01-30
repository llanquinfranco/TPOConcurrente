import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PuestoAtencion {
    
    private String aerolinea;
    private int cantidadMaxima;
    private int cantidadFila;
    private int cantidadHall;
    private Queue<String> filaCheckIn;  // Ver que puedo usar mejor para obtener el ultimo lugar
    private Lock accesoPuesto;
    private Condition esperaHall;
    private Condition esperaFila;
    private Condition esperaGuardia;
    

    public PuestoAtencion(String aerolinea, int cantidadMaxima) {
        this.aerolinea = aerolinea;
        this.cantidadMaxima = cantidadMaxima;
        this.cantidadFila = 0;
        this.cantidadHall = 0;
        this.filaCheckIn = new LinkedList<>();
        this.accesoPuesto = new ReentrantLock();
        this.esperaHall = accesoPuesto.newCondition();
        this.esperaFila = accesoPuesto.newCondition();
        this.esperaGuardia = accesoPuesto.newCondition();
    }

    // Metodo para Pasajero
    public void ingresarPuestoAtencion(String pasajero) throws InterruptedException {
        try {
            accesoPuesto.lock();
            cantidadHall++;
            while(cantidadFila == cantidadMaxima) {
                esperaHall.await();
            }
            System.out.println(pasajero + " ingresó a la fila del Puesto de Atencion de " + aerolinea);
            filaCheckIn.add(pasajero);
            cantidadFila++;
            cantidadHall--;
            esperaGuardia.signal();
        } finally {
            accesoPuesto.unlock();
        }
    }

    // Metodo para Pasajero
    public void realizarCheckIn(String pasajero) throws InterruptedException {
        try {
            accesoPuesto.lock();
            while(!pasajero.equals(filaCheckIn.peek())) {
                esperaFila.await();
            }
        } finally {
            accesoPuesto.unlock();
        }
    }

    // Metodo para Pasajero
    public int salirPuestoAtencion(String pasajero, Vuelo vuelo) throws InterruptedException {
        try {
            accesoPuesto.lock();
            int puestoAsignado = vuelo.getPuestoEmbarque();
            System.out.println(pasajero + " realizó el check-in del Vuelo de " + aerolinea);
            System.out.println(pasajero + " obtuvo el puesto de embarque: " + puestoAsignado);
            cantidadFila--;
            filaCheckIn.poll();
            esperaFila.signal();
            esperaGuardia.signal();
            return puestoAsignado;
        } finally {
            accesoPuesto.unlock();
        }
    }

    // Metodo para Guardia
    public void darPasoAPasajero() throws InterruptedException {
        try {
            accesoPuesto.lock();
            while(cantidadFila == cantidadMaxima || cantidadHall == 0) {
                esperaGuardia.await();
            }
            esperaHall.signal();
        } finally {
            accesoPuesto.unlock();
        }
    }

    public String getAerolinea() {
        return aerolinea;
    }

}
