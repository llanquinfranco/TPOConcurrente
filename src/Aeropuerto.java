public class Aeropuerto {


    
    private boolean estaAbierto;



    public Aeropuerto(Terminal[] terminales, Tren tren) {

        
        this.estaAbierto = true;
    }

    public synchronized void ingresarAeropuerto(String pasajero) {
        // Si el aeropuerto esta cerrado, el pasajero debe esperar a que abra
        try {
            while (!estaAbierto) {
                System.out.println("El " + pasajero + " esta esperando a que abra el aeropuerto");
                this.wait();
            }
            System.out.println("El " + pasajero + " entró al aeropuerto y se dirige al puesto de informes");
        } catch (Exception e) {
            
        }
    }

    public synchronized void abrirAeropuerto() {
        estaAbierto = true;
        System.out.println("El aeropuerto comenzo su horario de atencion.");
        System.out.println("Son las 6");
        this.notifyAll();
    }

    public synchronized void cerrarAeropuerto() {
        estaAbierto = false;
        System.out.println("El aeropuerto finalizo su horario de atencion.");
    }

}
