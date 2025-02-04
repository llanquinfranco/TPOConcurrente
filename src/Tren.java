import java.util.concurrent.CyclicBarrier;

import java.util.concurrent.BrokenBarrierException;

public class Tren {

    private int capacidad;
    private int ocupacion;
    private int debenBajar;
    private boolean sePuedenSubir;
    private boolean inicioRecorrido;
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
        this.inicioRecorrido = false;
        this.haLlegadoATerminal = false;
        this.sePuedenSubir = true; // Inicialmente se permiten subir pasajeros
    }

    // Método para Pasajero
    public void subirTren(String pasajero, char terminal) {
        try {
            synchronized (this) {
                // Espera hasta que se permita subir y haya espacio en el tren
                while (ocupacion >= capacidad || !sePuedenSubir) {
                    this.wait();
                }
                ocupacion++;
                System.out.println(pasajero + " subió al Tren con destino a la Terminal " + terminal + ". " + ocupacion + "/" + capacidad);
            }
            barrera.await(); // Espera a que el tren esté lleno
        } catch (InterruptedException | BrokenBarrierException e) {
            System.out.println("ERROR: Ocurrió un problema con el " + pasajero + " al intentar subir al Tren: " + e.getMessage());
        }
    }

    // Método para Pasajero
    public synchronized void bajarTren(String pasajero, char terminal) {
        try {
            // Espera hasta que el tren llegue a la terminal correcta
            while (terminal != terminalActual) {
                this.wait();
            }
            debenBajar++;
            // Espera hasta que el tren se detenga en la terminal
            while (!haLlegadoATerminal) {
                this.wait();
            }
            debenBajar--;
            ocupacion--;
            System.out.println(pasajero + " llegó a la Terminal " + terminal + ". " + ocupacion + "/" + capacidad);
            if (debenBajar == 0) { // Si no hay más pasajeros que deban bajar, notificar al chofer
                this.notifyAll();
            }
        } catch (InterruptedException e) {
            System.out.println("ERROR: Ocurrió un problema con el " + pasajero + " al intentar bajar del Tren: " + e.getMessage());
        }
    }

    // Acción para cuando se libera la barrera
    public synchronized void iniciar() {
        inicioRecorrido = true;
        System.out.println("El Tren está lleno y listo para iniciar el recorrido");
        this.notifyAll();
    }

    // Método para ControlTren
    public synchronized void habilitarAcceso() {
        sePuedenSubir = true;
        System.out.println("El Tren está listo para recibir pasajeros");
        this.notifyAll();
    }

    // Método para ControlTren
    public synchronized void iniciarRecorrido() {
        try {
            // Espera hasta que el tren esté lleno
            while (!inicioRecorrido) {
                this.wait();
            }
            sePuedenSubir = false; // Ya no se permiten más pasajeros
            System.out.println("El Tren ha iniciado el recorrido");
            this.notifyAll();
        } catch (InterruptedException e) {
            System.out.println("ERROR: Ocurrió un problema con el Chofer del Tren al intentar iniciar el recorrido: " + e.getMessage());
        }
    }

    // Método para ControlTren
    public synchronized void viajarATerminal(char terminal) {
        terminalActual = terminal;
        System.out.println("El Tren se dirige a la Terminal " + terminal);
        this.notifyAll();
    }

    // Método para ControlTren
    public synchronized void frenarEnTerminal() {
        if (debenBajar > 0) {
            haLlegadoATerminal = true;
            System.out.println("El Tren se detuvo en la Terminal " + terminalActual);
            this.notifyAll();
        }
    }

    // Método para ControlTren
    public synchronized void continuarRecorrido() {
        try {
            // Espera hasta que todos los pasajeros hayan bajado
            while (debenBajar > 0) {
                this.wait();
            }
            haLlegadoATerminal = false;
            this.notifyAll();
        } catch (InterruptedException e) {
            System.out.println("ERROR: Ocurrió un problema con el Chofer del Tren al intentar continuar con el recorrido: " + e.getMessage());
        }
    }

    // Método para ControlTren
    public synchronized void finalizarRecorrido() {
        try {
            inicioRecorrido = false;
            haLlegadoATerminal = false;
            sePuedenSubir = true; // Permitir que nuevos pasajeros suban
            System.out.println("El Tren finalizó el recorrido y está volviendo al inicio");
            this.notifyAll();
        } catch (Exception e) {
            System.out.println("ERROR: Ocurrió un problema con el Chofer del Tren al intentar finalizar el recorrido: " + e.getMessage());
        }
    }
}