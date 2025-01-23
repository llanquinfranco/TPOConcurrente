public class Vuelo {
    
    private String aerolinea;
    private Terminal terminal;
    private int puestoEmbarque;
    private int horaSalida;

    public Vuelo(String aerolinea, Terminal terminal, int puestoEmbarque, int horaSalida) {
        this.aerolinea = aerolinea;
        this.terminal = terminal;
        this.puestoEmbarque = puestoEmbarque;
        this.horaSalida = horaSalida;
    }

    public String getAerolinea() {
        return aerolinea;
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public int getPuestoEmbarque() {
        return puestoEmbarque;
    }

    public int getHoraSalida() {
        return horaSalida;
    }

}
