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
                Thread.sleep(500); // 5s simulan 1h
                tiempo.avanzarHora();
                if (!tiempo.estaAbierto()) {
                    aeropuerto.cerrarAeropuerto();
                    Thread.sleep(1500);         // Simulo cierre de 22 a 6
                    tiempo.saltar();
                    aeropuerto.abrirAeropuerto();      // Luego, abre de nuevo
                }
            } catch (Exception e) {
                System.out.println("Error con el reloj");
            }
        }

    }

}