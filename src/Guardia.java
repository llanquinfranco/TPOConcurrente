public class Guardia extends Thread {
    
    private PuestoAtencion puestoAtencion;

    public Guardia(PuestoAtencion puestoAtencion) {
        this.puestoAtencion = puestoAtencion;
    }

    public void run() {
        while (true) {
            try {
                puestoAtencion.darPasoAPasajero();
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println("Error con el guardia del puesto de atencion");
            }
        }
    }

}
