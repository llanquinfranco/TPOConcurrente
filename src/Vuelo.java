public class Vuelo {
    
    private String aerolinea;
    private Terminal terminal;
    private int puestoEmbarque;
    private int hora;

    public Vuelo(String aerolinea, Terminal terminal, int puestoEmbarque, int hora) {
        this.aerolinea = aerolinea;
        this.terminal = terminal;
        this.puestoEmbarque = puestoEmbarque;
        this.hora = hora;
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

    public int getHora() {
        return hora;
    }

}
