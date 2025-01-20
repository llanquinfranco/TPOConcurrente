public class Tiempo {
    
    private int hora;
    private boolean abierto;

    public Tiempo() {
        this.hora = 6;  // Inicializada a las 06hs (inicio de actividades)
        this.abierto = true;
    }

    public int getHora() {
        return hora;
    }

    public boolean estaAbierto() {
        return abierto;
    }

    public synchronized void avanzarHora() {
        this.hora++;
        if (this.hora == 22) {
            this.abierto = false;
        }
    }

    public synchronized void saltar() {
        this.hora = 6;
        this.abierto = true;
    }


}