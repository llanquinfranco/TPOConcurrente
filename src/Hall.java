import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Hall {
    
    private Map<String,Queue<String>> colasEspera;

    public Hall(String[] aerolineas) {
        this.colasEspera = new HashMap<>();
        for(int i = 0; i < aerolineas.length; i++) {
            this.colasEspera.put(aerolineas[i], new ConcurrentLinkedQueue<>());
        }
    }

    public synchronized void esperarEnHall(String pasajero, String aerolinea) {
        colasEspera.get(aerolinea).add(pasajero);
        System.out.println(pasajero + " espera en Hall Central");
        //this.wait();

    }

    public synchronized String buscarPasajero(String aerolinea) {
        return colasEspera.get(aerolinea).poll();
    }




    public Queue<String> getColaEspera(String aerolinea) {
        return colasEspera.get(aerolinea);
    }

}
