public class Tiempo {
    
    private int horaActual;
    private boolean abierto;

    public Tiempo() {
        this.horaActual = 6;  // Inicializada a las 06hs (inicio de actividades)
        this.abierto = true;
        System.out.println("Son las 6:00. El aeropuerto comenzó su horario de atencion");
        System.out.println();
    }

    // Metodo para Pasajero
    public boolean tieneTiempo(int horaSalida) {
        return horaActual + 3 < horaSalida;
    }

    // Metodo para Pasajero
    public synchronized void esperarLlamadoEmbarque(int horaSalida, Vuelo vuelo) {
        try {
            while(horaActual < horaSalida) {
                this.wait();
            }
            vuelo.notificarComienzoEmbarque();
        } catch (Exception e) {
            System.out.println("Error al esperar el llamado de embarque");
        }
    }

    // Metodo para Reloj
    public synchronized void avanzarHora() {
        this.horaActual++;
        System.out.println();
        System.out.println("Son las " + horaActual + ":00");
        System.out.println();
        if (this.horaActual == 22) {
            this.abierto = false;
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