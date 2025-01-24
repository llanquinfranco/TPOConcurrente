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
                System.out.println("El " + pasajero + " esta esperando en el hall");
                esperaHall.await();
            }
            System.out.println("El " + pasajero + " ingreso a la fila del puesto de atencion");
            filaCheckIn.add(pasajero);  // como hago para añadir el pasajero a la cola
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
            System.out.println("El " + pasajero + " esta realizando el check-in de su vuelo");
        } finally {
            accesoPuesto.unlock();
        }
    }

    // Metodo para Pasajero
    public void salirPuestoAtencion(String pasajero) throws InterruptedException {
        try {
            accesoPuesto.lock();
            System.out.println("El " + pasajero + " ya realizo el check-in y salio del puesto de atencion");
            cantidadFila--;
            filaCheckIn.poll();
            esperaFila.signal();
            esperaGuardia.signal();
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
            System.out.println("El guardia hizo pasar un pasajero ¿?");
            esperaHall.signal();
        } finally {
            accesoPuesto.unlock();
        }
    }

    public String getAerolinea() {
        return aerolinea;
    }

}
