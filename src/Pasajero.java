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
            // Para que los pasajeros entren a distintas horas del dia y no apenas abre el aeropuerto
            Thread.sleep(random.nextInt(1000, 50000));
            aeropuerto.ingresarAeropuerto(nombre);
            // Simula el tiempo que tarda el pasajero en llegar al puesto de informes luego de entrar al aeropuerto
            Thread.sleep(random.nextInt(500, 3000));

            String aerolinea = vuelo.getAerolinea();
            PuestoAtencion puestoAtencion = aeropuerto.ingresarPuestoInformes(nombre, aerolinea);

            // Simula el tiempo que tarda el pasajero en llegar al puesto de atencion luego de las indicaciones
            Thread.sleep(random.nextInt(500, 3000));
            puestoAtencion.ingresarPuestoAtencion(nombre);
            puestoAtencion.realizarCheckIn(nombre);
            // Simula el tiempo que tarda el pasajero en realizar el check-in de su vuelo
            Thread.sleep(random.nextInt(2000, 5000));
            int puestoEmbarque = puestoAtencion.salirPuestoAtencion(nombre, vuelo);

            Terminal terminal = vuelo.getTerminal();
            Tren tren = aeropuerto.getTren();

            // Simula el tiempo que tarda el pasajero en llegar al tren luego de salir del puesto de atencion
            Thread.sleep(random.nextInt(500, 3000));
            tren.subirTren(nombre);
            tren.bajarTren(terminal.getLetra(), nombre);

            int horaSalida = vuelo.getHoraSalida();
            // Si quiere y tiene tiempo, ingresa al FreeShop
            if (random.nextBoolean() && tiempo.tieneTiempo(horaSalida)) {
                FreeShop freeShop = terminal.getFreeShop();
                freeShop.ingresarFreeShop(nombre);
                // Simula el tiempo que tarda en mirar los productos del FreeShop
                Thread.sleep(random.nextInt(500, 3000));
                // Si va a comprar productos
                if(random.nextBoolean()) {
                    freeShop.comprar(nombre);
                }
                freeShop.salirFreeShop(nombre);
            }

            // Espera el llamado de embarque en la terminal
            tiempo.esperarLlamadoEmbarque(horaSalida, vuelo);
            vuelo.embarcarEsperarDespegue(nombre, puestoEmbarque);

        } catch (Exception e) {
            System.out.println("Error en el pasajero " + nombre);
        }
    }

}