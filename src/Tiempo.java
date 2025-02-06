public class Tiempo {
    
    private int horaActual;
    private boolean abierto;

    public Tiempo() {
        this.horaActual = 6;  // Inicializada a las 06hs (inicio de actividades)
        this.abierto = true;
    }

    // Metodo para Pasajero
    public boolean tieneTiempo(int horaSalida) {
        return horaActual + 3 < horaSalida;
    }

    // Metodo para Pasajero
    public synchronized void esperarLlamadoEmbarque(String pasajero, int horaSalida, Vuelo vuelo) {
        try {
            System.out.println(pasajero + " está esperando el llamado para embarcar");
            while(horaActual < horaSalida) {
                this.wait();
            }
            vuelo.notificarComienzoEmbarque();
        } catch (Exception e) {
            System.out.println("ERROR: Ocurrió un problema con el " + pasajero + " al esperar el llamado de embarque: " + e.getMessage());
        }
    }

    // Metodo para Reloj
    public synchronized void avanzarHora() {
        this.horaActual++;
        if (this.horaActual == 22) {
            this.abierto = false;
        } else {
            System.out.println();
            System.out.println("Son las " + horaActual + ":00");
            System.out.println();
        }
        this.notifyAll();
    }

    // Metodo para Reloj
    public synchronized void saltar() {
        this.horaActual = 6;
        this.abierto = true;
    }

    public int getHoraActual() {
        return horaActual;
    }

    public boolean estaAbierto() {
        return abierto;
    }

}