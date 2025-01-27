import java.util.concurrent.Semaphore;

public class FreeShop {
    
    // NO se menciona algun tipo de fila asi que directamente uso semaforos
    private Semaphore capacidad;
    private Semaphore cajas;

    public FreeShop(int espacio) {
        this.capacidad = new Semaphore(espacio);
        this.cajas = new Semaphore(2);
    }
    
    // Metodo para Pasajero
    public void ingresarFreeShop(String pasajero) throws InterruptedException {
        System.out.println("El " + pasajero + " esta intentando ingresar al FreeShop");
        capacidad.acquire();
        System.out.println("El " + pasajero + " pudo ingresar al FreeShop");
    }

    // Metodo para Pasajero
    public void comprar(String pasajero) throws InterruptedException {
        System.out.println("El " + pasajero + " esta esperando en una caja");
        cajas.acquire();
        System.out.println("El " + pasajero + " esta siendo atendido en una caja");
        cajas.release();
        System.out.println("El " + pasajero + " pago sus productos del FreeShop");
    }

    // Metodo para Pasajero
    public void salirFreeShop(String pasajero) {
        System.out.println("El " + pasajero + " salio ingresar al FreeShop");
        capacidad.release();
    }

}
