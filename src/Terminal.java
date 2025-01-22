public class Terminal {
    
    private char letra;
    private int[] puestos;
    private FreeShop freeShop;

    public Terminal(char letra, int[] puestos, FreeShop freeShop) {
        this.letra = letra;
        this.puestos = puestos;
        this.freeShop = freeShop;
    }

    public char getLetra() {
        return letra;
    }

    public FreeShop getFreeShop() {
        return freeShop;
    }
}
