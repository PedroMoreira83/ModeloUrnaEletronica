package models;

import java.time.LocalDate;

public class Eleitor extends Pessoa {
    private String tituloEleitor;
    private String zonaEleitoral;
    private String secaoEleitoral;

    public Eleitor(String nome, LocalDate dataNascimento, String tituloEleitor, String zonaEleitoral, String secaoEleitoral) {
        super(nome, dataNascimento);
        this.tituloEleitor = tituloEleitor;
        this.zonaEleitoral = zonaEleitoral;
        this.secaoEleitoral = secaoEleitoral;
    }

    public String getTituloEleitor() {
        return tituloEleitor;
    }

    // Implementação obrigatória do método abstrato (Polimorfismo)
    @Override
    public void exibirDados() {
        System.out.println("--- Dados do Eleitor ---");
        System.out.println("Nome: " + this.nome);
        System.out.println("Nascimento: " + this.getDataNascimentoFormatada());
        System.out.println("Titulo: " + this.tituloEleitor + " | Zona: " + this.zonaEleitoral + " | Secao: " + this.secaoEleitoral);
    }
}