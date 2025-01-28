import java.util.concurrent.CountDownLatch;

public class Vuelo {

    private String aerolinea;
    private Terminal terminal;
    private int puestoEmbarque;
    private int horaSalida;
    private int cantidadPasajeros;
    private boolean embarqueIniciado;
    private boolean yaDespego;
    private CountDownLatch latchEmbarque;

    public Vuelo(String aerolinea, Terminal terminal, int puestoEmbarque, int horaSalida) {
        this.aerolinea = aerolinea;
        this.terminal = terminal;
        this.puestoEmbarque = puestoEmbarque;
        this.horaSalida = horaSalida;
        this.cantidadPasajeros = 0;
        this.embarqueIniciado = false;
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
    public void embarcarEsperarDespegue(String pasajero) {
        try {
            System.out.println("El " + pasajero + " esta embarcando el vuelo de " + aerolinea);
            latchEmbarque.countDown();
            latchEmbarque.await();
            synchronized (this) {
                if (!yaDespego) {
                    yaDespego = true;
                    System.out.println("El vuelo de " + aerolinea + " acaba de despegar");
                }
            }
        } catch (Exception e) {
            System.out.println("Error al embarcar y/o esperar el llamado de embarque");
        }
    }

    // Metodo para Tiempo
    public synchronized void notificarComienzoEmbarque() {
        if(!embarqueIniciado) {
            embarqueIniciado = true;
            System.out.println("El vuelo de " + aerolinea + " esta listo para comenzar a embarcar");
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
