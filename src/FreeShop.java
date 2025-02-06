import java.util.concurrent.Semaphore;

public class FreeShop {
    
    // NO se menciona algun tipo de fila asi que directamente uso semaforos
    private Semaphore capacidad;
    private Semaphore cajas;

    public FreeShop(int espacio) {
        this.capacidad = new Semaphore(espacio);
        this.cajas = new Semaphore(2);  // El free shop posee 2 cajas
    }
    
    // Metodo para Pasajero
    public void ingresarFreeShop(String pasajero) throws InterruptedException {
        capacidad.acquire();
        System.out.println(pasajero + " ingresó al FreeShop");
    }

    // Metodo para Pasajero
    public void comprar(String pasajero) throws InterruptedException {
        cajas.acquire();
        System.out.println(pasajero + " realizó una compra");
        cajas.release();
    }

    // Metodo para Pasajero
    public void salirFreeShop(String pasajero) {
        System.out.println(pasajero + " salió del FreeShop");
        capacidad.release();
    }

}
