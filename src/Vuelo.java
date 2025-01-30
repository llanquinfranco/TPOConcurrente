import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Vuelo {

    private String aerolinea;
    private Terminal terminal;
    private int[] puestosEmbarque;
    private int horaSalida;
    private int cantidadPasajeros;
    private boolean embarqueIniciado;
    private boolean yaDespego;
    private CountDownLatch latchEmbarque;
    private Random random = new Random();

    public Vuelo(String aerolinea, Terminal terminal, int[] puestosEmbarque, int horaSalida) {
        this.aerolinea = aerolinea;
        this.terminal = terminal;
        this.puestosEmbarque = puestosEmbarque;
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
        System.out.println("Vuelo de " + aerolinea + ": " + cantidadPasajeros + " reservas");
        latchEmbarque = new CountDownLatch(cantidadPasajeros);
    }

    // Metodo para Pasajero
    public void embarcarEsperarDespegue(String pasajero, int puestoEmbarque) {
        try {
            System.out.println("El " + pasajero + " esta embarcando el vuelo de " + aerolinea + " en el puesto de embarque " + puestoEmbarque);
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

    // Random para el pasajero
    public int getPuestoEmbarque() {
        return puestosEmbarque[random.nextInt(puestosEmbarque.length)];
    }

    public int getHoraSalida() {
        return horaSalida;
    }

}
