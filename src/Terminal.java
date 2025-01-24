public class Terminal {
    
    private char letra;
    private int[] puestosEmbarque;
    private FreeShop freeShop;

    public Terminal(char letra, int[] puestosEmbarque, FreeShop freeShop) {
        this.letra = letra;
        this.puestosEmbarque = puestosEmbarque;
        this.freeShop = freeShop;
    }

    public char getLetra() {
        return letra;
    }

    public int[] getPuestosEmbarque() {
        return puestosEmbarque;
    }

    public FreeShop getFreeShop() {
        return freeShop;
    }
}
