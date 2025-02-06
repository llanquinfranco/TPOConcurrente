import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PuestoAtencion {
    
    private String aerolinea;
    private int cantidadMaxima;
    private int cantidadFila;
    private int cantidadHall;
    private Queue<String> filaCheckIn;
    private Lock accesoPuesto;
    private Condition esperaHall;
    private Condition esperaFila;
    private Condition esperaGuardia;
    

    public PuestoAtencion(String aerolinea, int cantidadMaxima) {
        this.aerolinea = aerolinea;
        this.cantidadMaxima = cantidadMaxima;
        this.cantidadFila = 0;
        this.cantidadHall = 0;
        this.filaCheckIn = new ConcurrentLinkedQueue<>();
        this.accesoPuesto = new ReentrantLock();
        this.esperaHall = accesoPuesto.newCondition();
        this.esperaFila = accesoPuesto.newCondition();
        this.esperaGuardia = accesoPuesto.newCondition();
    }

    // Método para Pasajero
    public void ingresarPuestoAtencion(String pasajero) throws InterruptedException {
        try {
            accesoPuesto.lock();
            cantidadHall++;
            // Si la fila está llena, el pasajero debe esperar
            while(cantidadFila >= cantidadMaxima) {
                esperaHall.await();
            }
            System.out.println(pasajero + " ingresó a la fila del Puesto de Atención de " + aerolinea);
            filaCheckIn.add(pasajero);
            cantidadFila++;
            cantidadHall--;
            esperaGuardia.signalAll();
        } finally {
            accesoPuesto.unlock();
        }
    }

    // Método para Pasajero
    public void realizarCheckIn(String pasajero) throws InterruptedException {
        try {
            accesoPuesto.lock();
            // El pasajero debe esperar su turno (el primero de la fila)
            while(!pasajero.equals(filaCheckIn.peek())) {
                esperaFila.await();
            }
            System.out.println(pasajero + " esta siendo atendido en el Puesto de Atención de " + aerolinea);
        } finally {
            accesoPuesto.unlock();
        }
    }

    // Método para Pasajero
    public int salirPuestoAtencion(String pasajero, Vuelo vuelo) throws InterruptedException {
        try {
            accesoPuesto.lock();
            int puestoAsignado = vuelo.getPuestoEmbarque();
            System.out.println(pasajero + " realizó el check-in del Vuelo de " + aerolinea);
            System.out.println(pasajero + " obtuvo el puesto de embarque: " + puestoAsignado);
            filaCheckIn.poll(); // Eliminar al pasajero de la fila
            cantidadFila--;
            esperaFila.signalAll(); // Despertar al siguiente pasajero
            esperaGuardia.signalAll(); // Despertar al guardia
            esperaHall.signalAll(); // Despertar a los pasajeros del hall tambien
            return puestoAsignado;
        } finally {
            accesoPuesto.unlock();
        }
    }

    // Método para Guardia
    public void darPasoAPasajero() throws InterruptedException {
        try {
            accesoPuesto.lock();
            // Si la fila está llena o no hay pasajeros en el hall, el guardia debe esperar
            while(cantidadFila >= cantidadMaxima || cantidadHall == 0) {
                esperaGuardia.await();
            }
            esperaHall.signalAll(); // Deja pasar a un pasajero a la fila
        } finally {
            accesoPuesto.unlock();
        }
    }

    public String getAerolinea() {
        return aerolinea;
    }

}

