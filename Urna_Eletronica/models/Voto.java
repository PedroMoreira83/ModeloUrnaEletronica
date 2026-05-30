package models;

public class Voto {
    private Cargo cargo;
    private TipoVoto tipo;
    private int numeroCandidato; // Usamos -1 para representar votos brancos e nulos

    public Voto(Cargo cargo, TipoVoto tipo, int numeroCandidato) {
        this.cargo = cargo;
        this.tipo = tipo;
        this.numeroCandidato = numeroCandidato;
    }

    public Cargo getCargo() { 
        return cargo; 
    }
    
    public TipoVoto getTipo() { 
        return tipo; 
    }
    
    public int getNumeroCandidato() { 
        return numeroCandidato; 
    }
}