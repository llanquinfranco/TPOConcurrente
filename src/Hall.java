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




}
