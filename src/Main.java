import java.util.Arrays;
import java.util.Random;

public class Main {
    
    private static final int cantTerminales = 4;
    private static final int cantPuestosEmbarque = 6;   // Cantidad por terminal
    private static final int capacidadTren = 4;
    private static final int capacidadFreeShop = 8;
    private static final int cantPasajeros = 12;    // Multiplo de capacidadTren para que no se bloqueen
    private static final Terminal[] terminales = new Terminal[cantTerminales];
    private static final String[] aerolineas = new String[]{"Emirates", "Air France", "Qatar Airways", "American Airlines"};
    private static final PuestoAtencion[] puestosAtencion = new PuestoAtencion[aerolineas.length];
    private static final Vuelo[] vuelos = new Vuelo[aerolineas.length];
    private static final Random random = new Random();

    public static void main(String[] args) {
        
        crearTerminales();  
        crearAerolineas();
        crearVuelos();

        Tren tren = new Tren(capacidadTren);
        Aeropuerto aeropuerto = new Aeropuerto(puestosAtencion, tren);
        Tiempo tiempo = new Tiempo();

        crearPasajeros(aeropuerto, tiempo);

        Reloj reloj = new Reloj(aeropuerto, tiempo);
        aeropuerto.abrirAeropuerto();
        
        ControlTren chofer = new ControlTren(tren, terminales);
        chofer.start();

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
            int horaSalida = random.nextInt(6, 23);
            vuelos[i] = new Vuelo(aerolineas[i], terminal, puestosEmbarque, horaSalida);
            System.out.println("Vuelo creado");
            System.out.println("Aerolinea: " + aerolineas[i]);
            System.out.println("Hora de salida: " + horaSalida + ":00");
            System.out.println("Terminal asignada: " + terminal.getLetra());
            System.out.println("Puestos de embarque disponibles: " + Arrays.toString(puestosEmbarque));
            System.out.println("----------------------------------------------------------------");
        }
    }

    public static void crearPasajeros(Aeropuerto aeropuerto, Tiempo tiempo) {
        // Creo los hilos de Pasajero y le asigno un vuelo del arreglo
        for (int i = 0; i < cantPasajeros; i++) {
            Vuelo vuelo = vuelos[random.nextInt(vuelos.length)];
            vuelo.registrarReserva();
            Pasajero pasajero = new Pasajero("Pasajero " + (i + 1), aeropuerto, vuelo, tiempo);
            System.out.println("Pasajero "+ (i + 1) + ": Adquirió reserva para el vuelo de " + vuelo.getAerolinea());
            pasajero.start();
        }
        System.out.println("----------------------------------------------------------------");
        // Inicializo los CountDownLatch de cada vuelo
        for (int i = 0; i < vuelos.length; i++) {
            vuelos[i].inicializarCountDownLatch();
        }
        System.out.println("----------------------------------------------------------------");
    }

}
