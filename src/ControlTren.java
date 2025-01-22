public class ControlTren extends Thread {
    
    private Tren tren;
    private Terminal[] terminales;

    public ControlTren(Tren tren, Terminal[] terminales) {
        this.tren = tren;
        this.terminales = terminales;
    }

    @Override
    public void run() {
        try {
            
        } catch (Exception e) {
            System.out.println("Error en el chofer del tren");
        }
    }




}
