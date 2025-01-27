import java.util.concurrent.CountDownLatch;

public class Vuelo {

    private String aerolinea;
    private Terminal terminal;
    private int puestoEmbarque;
    private int horaSalida;
    private int cantidadPasajeros;
    private boolean yaDespego;
    private CountDownLatch latchEmbarque;

    public Vuelo(String aerolinea, Terminal terminal, int puestoEmbarque, int horaSalida) {
        this.aerolinea = aerolinea;
        this.terminal = terminal;
        this.puestoEmbarque = puestoEmbarque;
        this.horaSalida = horaSalida;
        this.cantidadPasajeros = 0;
        this.yaDespego = false;
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
    public void embarcar(String pasajero) {
        System.out.println("El " + pasajero + " esta embarcando el vuelo de " + aerolinea);
        latchEmbarque.countDown();
    }

    // Metodo para Pasajero
    public void esperarDespegue(String pasajero) throws InterruptedException {
        System.out.println("El " + pasajero + " embarco el vuelo de " + aerolinea + " esta esperando el despegue");
        latchEmbarque.await();
        synchronized (this) {
            if (!yaDespego) {
                yaDespego = true;
                System.out.println("El vuelo de " + aerolinea + " acaba de despegar");
            }
        }

    }

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
