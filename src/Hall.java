import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Hall {
    
    private Map<String,Queue<String>> colasEspera;

    public Hall(String[] aerolineas) {
        // Creo una cola de espera para cada aerolinea en el Hall Central
        this.colasEspera = new HashMap<>();
        for(int i = 0; i < aerolineas.length; i++) {
            this.colasEspera.put(aerolineas[i], new ConcurrentLinkedQueue<>());
        }
    }

    // Metodo para Pasajero desde PuestoAtencion
    public synchronized void esperarEnHall(String pasajero, String aerolinea) {
        colasEspera.get(aerolinea).add(pasajero);
        System.out.println(pasajero + " espera en Hall Central");
    }

    // Metodo para Guardia desde PuestoAtencion
    public synchronized String buscarPasajero(String aerolinea) {
        return colasEspera.get(aerolinea).poll();
    }

    // Metodo para Guardia desde PuestoAtencion 
    public synchronized Queue<String> getColaEspera(String aerolinea) {
        return colasEspera.get(aerolinea);
    }

}
