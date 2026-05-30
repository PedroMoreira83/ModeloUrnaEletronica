package models;

import java.time.LocalDate;

public class Candidato extends Pessoa {
    private int numero;
    private String partido;
    private Cargo cargo;

    public Candidato(String nome, LocalDate dataNascimento, int numero, String partido, Cargo cargo) {
        super(nome, dataNascimento);
        this.numero = numero;
        this.partido = partido;
        this.cargo = cargo;
    }

    public int getNumero() {
        return numero;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public String getPartido() {
        return partido;
    }

    // Implementação obrigatória do método abstrato (Polimorfismo)
    @Override
    public void exibirDados() {
        System.out.println("--- Dados do Candidato ---");
        System.out.println("Nome: " + this.nome + " (" + this.partido + ")");
        System.out.println("Cargo: " + this.cargo + " | Número: " + this.numero);
    }
}