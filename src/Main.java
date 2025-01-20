public class Main {
    
    private static final int cantTerminales = 4;
    private static final int cantAerolineas = 4;
    private static final int cantPuestosEmbarque = 6;   // Cantidad por terminal

    private static final Terminal[] terminales = new Terminal[cantTerminales];
    private static final Aerolinea[] aerolineas = new Aerolinea[cantAerolineas];



    
    public static void main(String[] args) {
        
        crearTerminales();
        crearAerolineas();

        ControlTren chofer = new ControlTren();
        //Tren tren = new Tren(chofer);


        Aeropuerto aeropuerto = new Aeropuerto(terminales, aerolineas, tren);
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



        
    }



}
