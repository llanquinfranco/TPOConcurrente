public class Tiempo {
    
    private int horaActual;
    private boolean abierto;

    public Tiempo() {
        this.horaActual = 6;  // Inicializada a las 06hs (inicio de actividades)
        this.abierto = true;
    }

    // Metodo para Pasajero
    public boolean tieneTiempo(int horaSalida) {
        boolean puede = false;
        if(horaActual + 3 < horaSalida) {
            puede = true;
        }
        return puede;
    }

    // Metodo para Pasajero
    public synchronized void esperarLlamadoEmbarque(int horaSalida) {
        try {
            while(horaActual < horaSalida) {
                this.wait();
            }
        } catch (Exception e) {
            System.out.println("Error al subirse al esperar el llamado de embarque");
        }
    }


    // Metodo para Reloj
    public synchronized void avanzarHora() {
        this.horaActual++;
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