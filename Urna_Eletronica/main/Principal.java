package main;

import models.Candidato;
import models.Cargo;
import models.Eleitor;
import models.Pleito;
import services.UrnaEletronica;
import views.TelaUrna;

import javax.swing.UIManager;
import java.time.LocalDate;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        // Aplica o tema visual do sistema operacional
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Nao foi possivel carregar o tema visual.");
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("=== MODULO DE CONFIGURACAO (MESARIO) ===");

        Pleito pleito = new Pleito(LocalDate.now());
        pleito.adicionarCargoEmDisputa(Cargo.SENADOR);
        pleito.adicionarCargoEmDisputa(Cargo.GOVERNADOR);
        pleito.adicionarCargoEmDisputa(Cargo.PRESIDENTE);

        // MOCK DE DADOS: Carregando candidatos direto na memoria para poupar tempo na apresentacao
        pleito.registrarCandidato(new Candidato("Aristoteles", LocalDate.of(1950, 1, 1), 111, "Partido Filosofico (PF)", Cargo.SENADOR));
        pleito.registrarCandidato(new Candidato("Platao", LocalDate.of(1960, 2, 2), 222, "Partido da Caverna (PC)", Cargo.SENADOR));
        
        pleito.registrarCandidato(new Candidato("Marie Curie", LocalDate.of(1867, 11, 7), 55, "Partido da Quimica (PQ)", Cargo.GOVERNADOR));
        pleito.registrarCandidato(new Candidato("Isaac Newton", LocalDate.of(1643, 1, 4), 66, "Partido da Fisica (PFis)", Cargo.GOVERNADOR));
        
        pleito.registrarCandidato(new Candidato("Alan Turing", LocalDate.of(1912, 6, 23), 12, "Partido da Computacao (PC)", Cargo.PRESIDENTE));
        pleito.registrarCandidato(new Candidato("Ada Lovelace", LocalDate.of(1815, 12, 10), 34, "Partido dos Algoritmos (PA)", Cargo.PRESIDENTE));

        System.out.println("[OK] Candidatos carregados na memoria. Sua colinha para testar na urna:");
        System.out.println("-> SENADOR (3 digitos): Aristoteles (111) ou Platao (222)");
        System.out.println("-> GOVERNADOR (2 digitos): Marie Curie (55) ou Isaac Newton (66)");
        System.out.println("-> PRESIDENTE (2 digitos): Alan Turing (12) ou Ada Lovelace (34)");

        System.out.print("\nDigite a quantidade de eleitores permitidos para esta secao: ");
        int qtdEleitores = scanner.nextInt();
        
        for (int i = 1; i <= qtdEleitores; i++) {
            String titulo = "100" + i;
            pleito.cadastrarEleitor(new Eleitor("Eleitor " + i, LocalDate.of(2000, 1, 1), titulo, "001", "001"));
        }
        
        System.out.println("[OK] Eleitores cadastrados. Titulos validos gerados: 1001 ate 100" + qtdEleitores);
        System.out.println("\nConfiguracao concluida! Abrindo a Urna para o primeiro eleitor...");
        
        scanner.close(); // Fechando para evitar o aviso amarelo
        
        // Inicia a Urna
        UrnaEletronica urna = new UrnaEletronica(pleito);
        TelaUrna tela = new TelaUrna(urna);
        tela.setVisible(true);
    }
}