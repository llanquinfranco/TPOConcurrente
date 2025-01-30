public class Guardia extends Thread {
    
    private PuestoAtencion puestoAtencion;

    public Guardia(PuestoAtencion puestoAtencion) {
        this.puestoAtencion = puestoAtencion;
    }

    public void run() {
        while (true) {
            try {
                // Avisa a los pasajeros cuando se libera un lugar del puesto de atencion
                puestoAtencion.darPasoAPasajero();
            } catch (Exception e) {
                System.out.println("ERROR: Ocurrió un problema con el Guardia del Puesto de Atencion: " + e.getMessage());
            }
        }
    }

}
