import java.util.concurrent.CountDownLatch;

public class Vuelo {
    
    private String aerolinea;
    private Terminal terminal;
    private int puestoEmbarque;
    private int horaSalida;
    private int cantidadPasajeros;
    private CountDownLatch latchEmbarque;

    public Vuelo(String aerolinea, Terminal terminal, int puestoEmbarque, int horaSalida) {
        this.aerolinea = aerolinea;
        this.terminal = terminal;
        this.puestoEmbarque = puestoEmbarque;
        this.horaSalida = horaSalida;
        this.cantidadPasajeros = 0;
    }

    // Metodo para Main
    public synchronized void registrarReserva() {
        cantidadPasajeros++;
    } 

    // Metodo para Main
    public synchronized void inicializarCountDownLatch() {
        latchEmbarque = new CountDownLatch(cantidadPasajeros);
    } 

    // Metodo para Pasajero








    public String getAerolinea() {
        return aerolinea;
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public int getPuestoEmbarque() {
        return puestoEmbarque;
    }

    public int getHoraSalida() {
        return horaSalida;
    }

}
