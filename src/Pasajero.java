import java.util.Random;

public class Pasajero extends Thread {

    private String nombre;
    private Aeropuerto aeropuerto;
    private Vuelo vuelo;
    private Tiempo tiempo;
    private Random random;  // Para modelar la parte en la que quiere entrar o comprar algo

    public Pasajero(String nombre, Aeropuerto aeropuerto, Vuelo vuelo, Tiempo tiempo) {
        this.nombre = nombre;
        this.aeropuerto = aeropuerto;
        this.vuelo = vuelo;
        this.tiempo = tiempo;
        this.random = new Random();
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
            if (random.nextBoolean() && tiempo.tieneTiempo(horaSalida)) {
                FreeShop freeShop = terminal.getFreeShop();
                freeShop.ingresarFreeShop(nombre);
                if(random.nextBoolean()) {
                    freeShop.comprar(nombre);
                }
                freeShop.salirFreeShop(nombre);
            }

            tiempo.esperarLlamadoEmbarque(horaSalida, vuelo);
            vuelo.embarcarEsperarDespegue(nombre);

        } catch (Exception e) {
            System.out.println("Error en el pasajero " + nombre);
        }

    }

}