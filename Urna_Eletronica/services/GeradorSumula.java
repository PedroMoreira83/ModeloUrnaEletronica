package services;

import models.Candidato;
import models.Cargo;
import models.TipoVoto;
import models.Voto;

import java.util.List;

public class GeradorSumula {
    private UrnaEletronica urna;

    public GeradorSumula(UrnaEletronica urna) {
        this.urna = urna;
    }

    public void gerarRelatorioFinal() {
        List<Voto> todosVotos = urna.getVotosRegistrados();
        int totalEleitores = urna.getPleito().getTotalEleitores();

        System.out.println("\n========================================");
        System.out.println("          SUMULA ELEITORAL FINAL          ");
        System.out.println("========================================");
        System.out.println("Total de Eleitores Esperados na Secao: " + totalEleitores);
        System.out.println("----------------------------------------");

        // O segredo: Iterar sobre cada cargo separadamente!
        for (Cargo cargo : Cargo.values()) {
            System.out.println("\n>>> RESULTADO PARA " + cargo + " <<<");

            int votosValidosCargo = 0;
            int votosBrancosCargo = 0;
            int votosNulosCargo = 0;

            // 1. Contar os votos apenas para o cargo atual do laço
            for (Voto v : todosVotos) {
                if (v.getCargo() == cargo) {
                    if (v.getTipo() == TipoVoto.VALIDO) votosValidosCargo++;
                    else if (v.getTipo() == TipoVoto.BRANCO) votosBrancosCargo++;
                    else if (v.getTipo() == TipoVoto.NULO) votosNulosCargo++;
                }
            }

            int totalVotosCargo = votosValidosCargo + votosBrancosCargo + votosNulosCargo;
            System.out.println("Total de Votos Depositados: " + totalVotosCargo);
            System.out.println("Validos: " + votosValidosCargo + " | Brancos: " + votosBrancosCargo + " | Nulos: " + votosNulosCargo);

            // 2. Calcular a porcentagem em cima apenas dos votos validos DESTE cargo
            if (votosValidosCargo > 0) {
                System.out.println("- Votos por Candidato:");
                
                for (Candidato c : urna.getPleito().getCandidatosRegistrados()) {
                    // Filtra para exibir apenas os candidatos que estão concorrendo a este cargo
                    if (c.getCargo() == cargo) {
                        int votosRecebidos = 0;
                        for (Voto v : todosVotos) {
                            if (v.getCargo() == cargo && v.getTipo() == TipoVoto.VALIDO && v.getNumeroCandidato() == c.getNumero()) {
                                votosRecebidos++;
                            }
                        }
                        
                        // Cálculo corrigido!
                        double percentual = ((double) votosRecebidos / votosValidosCargo) * 100;
                        System.out.printf("  %s (%d): %d votos (%.2f%%)\n", c.getNome(), c.getNumero(), votosRecebidos, percentual);
                    }
                }
            } else {
                System.out.println("- Nenhum voto valido registrado para este cargo.");
            }
            System.out.println("----------------------------------------");
        }
        System.out.println("========================================");
    }
}