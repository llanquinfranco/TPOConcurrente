public class Main {
    
    private static final int cantTerminales = 4;
    private static final int cantAerolineas = 4;
    private static final int cantPuestosEmbarque = 6;   // Cantidad por terminal

    private static final Terminal[] terminales = new Terminal[cantTerminales];
    private static final String[] aerolineas = new String[]{"Emirates", "Air France", "Qatar Airways", "American Airlines"};



    
    public static void main(String[] args) {




        
        crearTerminales();  
        crearAerolineas();

        ControlTren chofer = new ControlTren();
        //Tren tren = new Tren(chofer);


        //Aeropuerto aeropuerto = new Aeropuerto(terminales, aerolineas, tren);


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
        
        // aca creo el puesto de atencion de cada aerolinea
        // uno x cada aerolinea

        
    }



}
