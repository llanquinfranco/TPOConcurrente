public class Main {
    
    private static final int cantTerminales = 4;
    private static final int cantPuestosEmbarque = 6;   // Cantidad por terminal

    private static final Terminal[] terminales = new Terminal[cantTerminales];
    private static final String[] aerolineas = new String[]{"Emirates", "Air France", "Qatar Airways", "American Airlines"};
    private static final PuestoAtencion[] puestosAtencion = new PuestoAtencion[aerolineas.length];


    
    public static void main(String[] args) {
        
        crearTerminales();  
        crearAerolineas();

        Tren tren = new Tren();
        ControlTren chofer = new ControlTren(tren, terminales);
        chofer.start();


        Aeropuerto aeropuerto = new Aeropuerto(puestosAtencion, tren);


        Tiempo tiempo = new Tiempo();
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
            FreeShop freeShop = new FreeShop();
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



}
