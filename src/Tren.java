import java.util.concurrent.CyclicBarrier;

public class Tren {

    private int capacidad;
    private int ocupacion;
    private int debenBajar;
    private boolean estaEnRecorrido;
    private boolean haLlegadoATerminal;
    private char terminalActual;
    private CyclicBarrier barrera;

    public Tren(int capacidad) {
        this.capacidad = capacidad;
        this.ocupacion = 0;
        this.debenBajar = 0;
        Runnable accion = () -> {
            iniciar();
        };
        this.barrera = new CyclicBarrier(capacidad, accion);
        this.estaEnRecorrido = false;
        this.haLlegadoATerminal = false;
    }

    // Metodo para Pasajero
    public void subirTren(String pasajero) { // synchronized o se bloquea?
        try {
            synchronized (this) {
                while (estaEnRecorrido || ocupacion >= capacidad) {
                    this.wait();
                }
                ocupacion++;
                System.out.println("El " + pasajero + " subio al tren. " + ocupacion + "/" + capacidad);
            }
            barrera.await(); // Espera a que el tren este lleno
        } catch (Exception e) {
            System.out.println("Error en el " + pasajero + " al subirse al tren");
        }
    }

    // Metodo para Pasajero
    public synchronized void bajarTren(char terminal, String pasajero) {
        try {
            while (terminal != terminalActual) {
                this.wait();
            }
            debenBajar++;
            while (!haLlegadoATerminal) {
                this.wait();
            }
            debenBajar--;
            ocupacion--;
            System.out.println("El " + pasajero + " se bajo en la terminal " + terminal);
            if (debenBajar == 0) { // Baja y si es el ultimo que le avise al chofer
                this.notifyAll();
            }
        } catch (Exception e) {
            System.out.println("Error en el pasajero al bajarse del tren");
        }
    }

    // Accion para cuando se libera la barrera
    public synchronized void iniciar() {
        estaEnRecorrido = true;
        notifyAll();
    }

    // Metodo para ControlTren
    public synchronized void iniciarRecorrido() {
        try {
            while (!estaEnRecorrido) {
                this.wait();
            }
            System.out.println("El tren inicio el recorrido");
        } catch (Exception e) {
            System.out.println("Error en el chofer al iniciar el recorrido");
        }
    }

    // Metodo para ControlTren
    public synchronized void viajarATerminal(char terminal) {
        terminalActual = terminal;
        System.out.println("El tren se dirige a la terminal " + terminal);
        this.notifyAll();
    }

    // Metodo para ControlTren
    public synchronized void frenarEnTerminal() {
        if (debenBajar > 0) {
            haLlegadoATerminal = true;
            System.out.println("El tren se detuvo en la terminal " + terminalActual);
            this.notifyAll();
        }
    }

    // Metodo para ControlTren
    public synchronized void continuarRecorrido() {
        try {
            while (debenBajar > 0) {
                this.wait();
            }
            haLlegadoATerminal = false;
        } catch (Exception e) {
            System.out.println("Error en el chofer al continuar con el recorrido");
        }
    }

    // Metodo para ControlTren
    public synchronized void finalizarRecorrido() {
        try {
            terminalActual = ' ';
            ocupacion = 0;
            estaEnRecorrido = false;
            System.out.println("El tren finalizo el recorrido");
            this.notifyAll();
        } catch (Exception e) {
            System.out.println("Error en el chofer al finalizar con el recorrido");
        }
    }

}
