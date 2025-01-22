public class ControlTren extends Thread {
    
    private Tren tren;
    private Terminal[] terminales;

    public ControlTren(Tren tren, Terminal[] terminales) {
        this.tren = tren;
        this.terminales = terminales;
    }

    @Override
    public void run() {
        char terminal;
        while (true) {
            try {
                tren.iniciarRecorrido();;
                for (int i = 0; i < terminales.length; i++) {
                    terminal = terminales[i].getLetra();
                    tren.viajarATerminal(terminal);
                    Thread.sleep(500);
                    tren.frenarEnTerminal();
                    tren.continuarRecorrido();
                }
                tren.finalizarRecorrido();
            } catch (Exception e) {
                System.out.println("Error en el chofer del tren");
            }
        }
    }

}
