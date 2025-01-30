public class Aeropuerto {

    private PuestoAtencion[] puestosAtencion;
    private Tren tren;
    private boolean estaAbierto;

    public Aeropuerto(PuestoAtencion[] puestosAtencion, Tren tren) {
        this.puestosAtencion = puestosAtencion;
        this.tren = tren;
        this.estaAbierto = false;
    }
    
    // Metodo para Pasajero
    public synchronized void ingresarAeropuerto(String pasajero) {
        // Si el aeropuerto esta cerrado, el pasajero debe esperar a que abra
        try {
            while (!estaAbierto) {
                this.wait();
            }
            System.out.println(pasajero + " ingresó al Aeropuerto");
        } catch (Exception e) {
            System.out.println("ERROR: Ocurrió un problema con el " + pasajero + " al intentar ingresar al Aeropuerto: " + e.getMessage());
        }
    }

    // Metodo para Pasajero
    public synchronized PuestoAtencion ingresarPuestoInformes(String pasajero, String aerolinea) {
        int i = 0;
        PuestoAtencion puesto = null;
        while(!aerolinea.equals(puestosAtencion[i].getAerolinea())) {
            i++;
        }
        puesto = puestosAtencion[i];
        System.out.println(pasajero + " llegó al Puesto de Informes y fue derivado al Puesto de Atencion de " + aerolinea);
        return puesto;
    }

    // Metodo para Main(una vez) y Reloj
    public synchronized void abrirAeropuerto() {
        estaAbierto = true;
        System.out.println();
        System.out.println("Son las 6:00. El Aeropuerto comenzó su horario de atencion");
        System.out.println();
        this.notifyAll();
    }

    // Metodo para Reloj
    public synchronized void cerrarAeropuerto() {
        System.out.println();
        System.out.println("Son las 22:00. El Aeropuerto finalizó su horario de atencion");
        System.out.println();
        estaAbierto = false;
        this.notifyAll();
    }

    public Tren getTren() {
        return tren;
    }
}
