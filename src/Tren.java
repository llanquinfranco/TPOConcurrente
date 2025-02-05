import java.util.concurrent.CyclicBarrier;

public class Tren {

    private int capacidad;
    private int ocupacion;
    private boolean sePuedenSubir;
    private boolean inicioRecorrido;
    private char terminalActual;
    private CyclicBarrier barrera;

    public Tren(int capacidad) {
        this.capacidad = capacidad;
        this.ocupacion = 0;
        Runnable accion = () -> {
            iniciar();
        };
        this.barrera = new CyclicBarrier(capacidad, accion);
        this.inicioRecorrido = false;
        this.sePuedenSubir = false;
    }

    // Metodo para Pasajero
    public  void subirTren(String pasajero, char terminal) {
        try {
            synchronized (this) {
                while (ocupacion >= capacidad || !sePuedenSubir) {
                    this.wait();
                }
                ocupacion++;
                System.out.println(pasajero + " subió al Tren con destino a la Terminal " + terminal + ". " + ocupacion + "/" + capacidad);
                if (ocupacion == capacidad) {
                    sePuedenSubir = false; 
                    notifyAll(); 
                }
            }
            barrera.await(); // Espera a que el tren este lleno
        } catch (Exception e) {
            System.out.println("ERROR: Ocurrió un problema con el " + pasajero + " al intentar subir al Tren: " + e.getMessage());
        }
    }

    // Metodo para Pasajero
    public synchronized void bajarTren(String pasajero, char terminal) {
        try {
            while (terminal != terminalActual) {
                this.wait();
            }
            ocupacion--;
            System.out.println(pasajero + " se bajó en la Terminal " + terminal + ". " + ocupacion + "/" + capacidad);
            if (ocupacion == 0) { // Baja y si es el ultimo que le avise al chofer
                this.notifyAll();
            }
        } catch (Exception e) {
            System.out.println("ERROR: Ocurrió un problema con el " + pasajero + " al intentar bajar del Tren: " + e.getMessage());
        }
    }

    // Accion para cuando se libera la barrera
    public synchronized void iniciar() {
        inicioRecorrido = true;
        this.notifyAll();
    }

    // Metodo para ControlTren
    public synchronized void habilitarAcceso() {
        sePuedenSubir = true;
        System.out.println("El Tren esta listo para recibir pasajeros");
        this.notifyAll();
    }

    // Metodo para ControlTren
    public synchronized void iniciarRecorrido() {
        try {
            while (!inicioRecorrido) {
                this.wait();
            }
            System.out.println("El Tren ha iniciado el recorrido");
        } catch (Exception e) {
            System.out.println("ERROR: Ocurrió un problema con el Chofer del Tren al intentar iniciar el recorrido: " + e.getMessage());
        }
    }

    // Metodo para ControlTren
    public synchronized void viajarATerminal(char terminal) {
        terminalActual = terminal;
        System.out.println("El Tren llegó a la Terminal " + terminal);
        this.notifyAll();
    }

    // Metodo para ControlTren
    public synchronized void finalizarRecorrido() {
        try {
            inicioRecorrido = false;
            System.out.println("El Tren finalizó el recorrido y esta volviendo al inicio");
            this.notifyAll();
        } catch (Exception e) {
            System.out.println("ERROR: Ocurrió un problema con el Chofer del Tren al intentar finalizar el recorrido: " + e.getMessage());
        }
    }

}