import java.util.Random;

public class Main {
    
    private static final int cantTerminales = 4;
    private static final int cantPuestosEmbarque = 6;   // Cantidad por terminal
    private static final int capacidadTren = 10;
    private static final int capacidadFreeShop = 8;
    private static final int cantPasajeros = capacidadTren * 10;
    private static final Terminal[] terminales = new Terminal[cantTerminales];
    private static final String[] aerolineas = new String[]{"Emirates", "Air France", "Qatar Airways", "American Airlines"};
    private static final PuestoAtencion[] puestosAtencion = new PuestoAtencion[aerolineas.length];
    private static final Vuelo[] vuelos = new Vuelo[aerolineas.length];
    private static final Random random = new Random();

    // Cantidad de pasajeros igual a multiplo de capacidad tren para que no se bloqueen y fue
    
    public static void main(String[] args) {
        
        crearTerminales();  
        crearAerolineas();
        crearVuelos();

        Tren tren = new Tren(capacidadTren);
        ControlTren chofer = new ControlTren(tren, terminales);
        chofer.start();


        Aeropuerto aeropuerto = new Aeropuerto(puestosAtencion, tren);
        Tiempo tiempo = new Tiempo();

        
        crearPasajeros(aeropuerto, tiempo);


        




        
        Reloj reloj = new Reloj(aeropuerto, tiempo);
        reloj.start();










    }

    public static void crearTerminales() {
        char letraTerminal = 'A';
        int numeroPuerta = 1;
        for (int i = 0; i < terminales.length; i++) {
            int[] puestosEmbarque = new int[cantPuestosEmbarque];
            for (int j = 0; j < puestosEmbarque.length; j++) {
                puestosEmbarque[j] = numeroPuerta;
                numeroPuerta++;
            }
            FreeShop freeShop = new FreeShop(capacidadFreeShop);
            terminales[i] = new Terminal(letraTerminal, puestosEmbarque, freeShop);
            letraTerminal++;
        }
    }

    public static void crearAerolineas() {
        int maxima = 3; // Fija por ahora
        for(int i = 0; i < aerolineas.length; i++) {
            puestosAtencion[i] = new PuestoAtencion(aerolineas[i], maxima);
        }
    }

    public static void crearVuelos() {

        for (int i = 0; i < aerolineas.length; i++) {
            Terminal terminal = terminales[random.nextInt(terminales.length)];
            int[] puestosEmbarque = terminal.getPuestosEmbarque();
            int puestoEmbarque = puestosEmbarque[random.nextInt(puestosEmbarque.length)];
            int horaSalida = random.nextInt(6, 23);
            vuelos[i] = new Vuelo(aerolineas[i], terminal, puestoEmbarque, horaSalida);
        }



    }

    public static void crearPasajeros(Aeropuerto aeropuerto, Tiempo tiempo) {
        // Creo los hilos de Pasajero y le asigno un vuelo del arreglo
        for (int i = 0; i < cantPasajeros; i++) {
            Vuelo vuelo = vuelos[random.nextInt(vuelos.length)];
            vuelo.registrarReserva();
            Pasajero pasajero = new Pasajero("Pasajero " + (i + 1), aeropuerto, vuelo, tiempo);
            pasajero.start();
        }
        // Inicializo los CountDownLatch de cada vuelo
        for (int i = 0; i < vuelos.length; i++) {
            vuelos[i].inicializarCountDownLatch();
        }
    }

}
