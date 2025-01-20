public class Pasajero extends Thread {

    private String nombre;
    private Aeropuerto aeropuerto;
    private Vuelo vuelo;
    private Reloj reloj;

    public Pasajero(String nombre, Aeropuerto aeropuerto, Vuelo vuelo, Reloj reloj) {
        this.nombre = nombre;
        this.aeropuerto = aeropuerto;
        this.vuelo = vuelo;
        this.reloj = reloj;
    }
    
    @Override
    public void run() {
        try {
            





            
        } catch (Exception e) {
            System.out.println("Error en el pasajero " + nombre);
        }
    }





}