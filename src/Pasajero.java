import java.util.Random;

public class Pasajero extends Thread {

    private String nombre;
    private Aeropuerto aeropuerto;
    private Vuelo vuelo;
    private Tiempo tiempo;
    private Random randomQuiereEntrar;

    public Pasajero(String nombre, Aeropuerto aeropuerto, Vuelo vuelo, Tiempo tiempo) {
        this.nombre = nombre;
        this.aeropuerto = aeropuerto;
        this.vuelo = vuelo;
        this.tiempo = tiempo;
        this.randomQuiereEntrar = new Random();
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

            Terminal terminal = vuelo.getTerminal();
            Tren tren = aeropuerto.getTren();
            tren.subirTren(nombre);
            tren.bajarTren(terminal.getLetra(), nombre);

            int horaSalida = vuelo.getHoraSalida();
            if (randomQuiereEntrar.nextBoolean() && tiempo.puedeEntrar(horaSalida)) {

            }

            
            // If randomboolean para ver si quiere entrar AND tienetiempo
            // freeShop.ingresarFreeShop();
            // Existe posibilidad de que pierda el vuelo o muy falopa¿?
            // vuelo.abordarAvion();


            
        } catch (Exception e) {
            System.out.println("Error en el pasajero " + nombre);
        }
    }





}