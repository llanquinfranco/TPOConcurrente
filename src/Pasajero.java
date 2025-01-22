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
            aeropuerto.ingresarAeropuerto(nombre);

            String aerolinea = vuelo.getAerolinea();
            PuestoAtencion puestoAtencion = aeropuerto.ingresarPuestoInformes(aerolinea);

            puestoAtencion.ingresarPuestoAtencion(nombre);
            puestoAtencion.realizarCheckIn(nombre);
            puestoAtencion.salirPuestoAtencion(nombre);


            tren.subirAlTren();
            // If randomboolean para ver si quiere entrar AND tienetiempo
            // freeShop.ingresarFreeShop();
            // Existe posibilidad de que pierda el vuelo o muy falopa¿?
            vuelo.abordarAvion();


            
        } catch (Exception e) {
            System.out.println("Error en el pasajero " + nombre);
        }
    }





}