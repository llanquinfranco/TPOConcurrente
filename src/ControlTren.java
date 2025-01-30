import java.util.Random;

public class ControlTren extends Thread {
    
    private Tren tren;
    private Terminal[] terminales;
    private Random random = new Random();

    public ControlTren(Tren tren, Terminal[] terminales) {
        this.tren = tren;
        this.terminales = terminales;
    }

    @Override
    public void run() {
        char terminal;
        while (true) {
            try {
                tren.habilitarAcceso();
                tren.iniciarRecorrido();
                for (int i = 0; i < terminales.length; i++) {
                    terminal = terminales[i].getLetra();
                    tren.viajarATerminal(terminal);
                    // Simula el tiempo que tarda el tren en llegar a la siguiente terminal
                    Thread.sleep(random.nextInt(250, 1000));
                    tren.frenarEnTerminal();
                    tren.continuarRecorrido();
                }
                tren.finalizarRecorrido();
                // Simula el tiempo que tarda el tren en volver al inicio
                Thread.sleep(random.nextInt(500, 2500));
            } catch (Exception e) {
                System.out.println("Error en el chofer del tren");
            }
        }
    }

}
