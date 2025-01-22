import java.util.concurrent.CyclicBarrier;

public class Tren {
    
    private int capacidad;
    private int cantidadTerminal;
    private boolean lleno;
    private boolean enViaje;
    private char terminalActual;
    private CyclicBarrier barrera;

    public Tren(int capacidad) {
        Runnable accion = () -> {
            iniciar();
        };
        this.barrera = new CyclicBarrier(capacidad, accion);
        this.cantidadTerminal = 0;
        this.lleno = false;
        this.enViaje = false;
    }



    // Metodo para Pasajero
    public void subirTren(char terminal) {

    }

    // Metodo para Pasajero
    public void bajarTren() {

    }

    // Metodo para ControlTren
    public void iniciarRecorrido() {

    }

    // Metodo para ControlTren
    public void viajarATerminal(char terminal) {

    }

    // Metodo para ControlTren
    public void frenarEnTerminal() {

    }

    // Metodo para ControlTren
    public void continuarRecorrido() {

    }

    // Metodo para ControlTren
    public void finalizarRecorrido() {

    }

    // Accion para cuando se libera la barrera
    public void iniciar() {

    }



}
