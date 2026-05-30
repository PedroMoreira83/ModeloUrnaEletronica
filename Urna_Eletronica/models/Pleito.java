package models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pleito {
    private LocalDate dataEleicao;
    private List<Cargo> cargosEmDisputa;
    private List<Candidato> candidatosRegistrados;
    private List<Eleitor> eleitoresAutorizados;

    public Pleito(LocalDate dataEleicao) {
        this.dataEleicao = dataEleicao;
        this.cargosEmDisputa = new ArrayList<>();
        this.candidatosRegistrados = new ArrayList<>();
        this.eleitoresAutorizados = new ArrayList<>();
    }

    // Métodos de Configuração (Feature 1.2)
    public void adicionarCargoEmDisputa(Cargo cargo) {
        if (!cargosEmDisputa.contains(cargo)) {
            cargosEmDisputa.add(cargo);
        }
    }

    public void registrarCandidato(Candidato candidato) {
        this.candidatosRegistrados.add(candidato);
    }

    public void cadastrarEleitor(Eleitor eleitor) {
        this.eleitoresAutorizados.add(eleitor);
    }

    public LocalDate getDataEleicao() {
        return dataEleicao;
    }

    // Buscas que usaremos na hora da votação (Épico 2)
    public Eleitor buscarEleitorPorTitulo(String titulo) {
        for (Eleitor e : eleitoresAutorizados) {
            if (e.getTituloEleitor().equals(titulo)) {
                return e;
            }
        }
        return null; // Mais à frente, trataremos isso com uma Exception customizada!
    }

    public Candidato buscarCandidatoPorNumeroECargo(int numero, Cargo cargo) {
        for (Candidato c : candidatosRegistrados) {
            if (c.getNumero() == numero && c.getCargo() == cargo) {
                return c;
            }
        }
        return null; // Trataremos com Voto Nulo ou Exception depois
    }

    // Getter para permitir que a Súmula leia os candidatos com segurança
    public List<Candidato> getCandidatosRegistrados() {
        return candidatosRegistrados;
    }

    // Retorna a quantidade de eleitores permitidos na sessão
    public int getTotalEleitores() {
        return eleitoresAutorizados.size();
    }
}