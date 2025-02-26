import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PuestoAtencion {
    
    private String aerolinea;
    private int capacidad;
    private int ocupacion;
    private Queue<String> filaCheckIn;
    private Lock accesoPuesto;
    private Condition esperaFila;
    private Condition esperaGuardia;
    private Hall hall;

    public PuestoAtencion(String aerolinea, int cantidadMaxima, Hall hall) {
        this.aerolinea = aerolinea;
        this.capacidad = cantidadMaxima;
        this.ocupacion = 0;
        this.filaCheckIn = new ConcurrentLinkedQueue<>();
        this.accesoPuesto = new ReentrantLock();
        this.esperaFila = accesoPuesto.newCondition();
        this.esperaGuardia = accesoPuesto.newCondition();
        this.hall = hall;
    }

    // Metodo para Pasajero
    public void ingresarPuestoAtencion(String pasajero) throws InterruptedException {
        try {
            accesoPuesto.lock();
            // Si el puesto esta lleno el pasajero espera en el hall, sino, entra
            if(ocupacion == capacidad) {
                hall.esperarEnHall(pasajero, aerolinea);
                esperaGuardia.signal();
            } else {
                System.out.println(pasajero + " ingresó a la fila del Puesto de Atención de " + aerolinea);
                filaCheckIn.add(pasajero);
                ocupacion++;
            }
        } finally {
            accesoPuesto.unlock();
        }
    }

    // Metodo para Pasajero
    public void realizarCheckIn(String pasajero) throws InterruptedException {
        try {
            accesoPuesto.lock();
            // El pasajero debe esperar su turno (el primero de la fila)
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
            filaCheckIn.poll(); // Eliminar al pasajero de la fila
            ocupacion--;
            esperaFila.signalAll(); // Despertar al siguiente pasajero
            esperaGuardia.signalAll(); // Despertar al guardia
            return puestoAsignado;
        } finally {
            accesoPuesto.unlock();
        }
    }

    // Metodo para Guardia
    public void darPasoAPasajero() throws InterruptedException {
        try {
            accesoPuesto.lock();
            // El guardia espera a que haya alguien esperando para entrar
            while(hall.getColaEspera(aerolinea).isEmpty()) {
                esperaGuardia.await();
            }
            // Luego espera a que se desocupe el puesto para hacerlo entrar
            while(ocupacion >= capacidad) {
                esperaGuardia.await();
            }
            String pasajero = hall.buscarPasajero(aerolinea);
            System.out.println(pasajero + " ingresó a la fila del Puesto de Atención de " + aerolinea);
            filaCheckIn.add(pasajero);
            ocupacion++;
        } finally {
            accesoPuesto.unlock();
        }
    }

    public String getAerolinea() {
        return aerolinea;
    }

}


