public class Reloj extends Thread {

    private Tiempo tiempo;
    private Aeropuerto aeropuerto;

    // Hilo (bucle infinito) que simula un reloj p controlar el tiempo y etc
    public Reloj(Aeropuerto aeropuerto, Tiempo tiempo) {
        this.aeropuerto = aeropuerto;
        this.tiempo = tiempo;
    }

    public void run() {
        while (true) {
            try {
                // 1 hora en el sistema = 5 segundos en la vida real
                Thread.sleep(5000);     
                tiempo.avanzarHora();
                if (!tiempo.estaAbierto()) {
                    aeropuerto.cerrarAeropuerto();
                    // Simulo cierre de 22 a 6
                    Thread.sleep(10000);
                    tiempo.saltar();
                    // Luego, el Aeropuerto abre de nuevo
                    aeropuerto.abrirAeropuerto();
                }
            } catch (Exception e) {
                System.out.println("ERROR: Ocurrió un problema con el Reloj: " + e.getMessage());
            }
        }

    }

}