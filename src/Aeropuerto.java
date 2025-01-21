public class Aeropuerto {

    private PuestoInformes puestoInformes;
    
    private boolean estaAbierto;



    public Aeropuerto(PuestoInformes puestoInformes) {
        this.puestoInformes = puestoInformes;
        
        this.estaAbierto = true;
    }
    
    // Metodo para Pasajero
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

    // Metodo para Reloj
    public synchronized void abrirAeropuerto() {
        estaAbierto = true;
        System.out.println("El aeropuerto comenzo su horario de atencion.");
        System.out.println("Son las 6");
        this.notifyAll();
    }

    // Metodo para Reloj
    public synchronized void cerrarAeropuerto() {
        estaAbierto = false;
        System.out.println("El aeropuerto finalizo su horario de atencion.");
    }

}
