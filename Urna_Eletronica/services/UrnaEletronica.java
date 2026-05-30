package services;

import exceptions.EleicaoInativaException;
import interfaces.OperacaoUrna;
import models.Cargo;
import models.Pleito;
import models.TipoVoto;
import models.Voto;

import java.util.ArrayList;
import java.util.List;

public class UrnaEletronica implements OperacaoUrna {
    private Pleito pleito;
    private List<Voto> votosRegistrados;
    private boolean eleicaoAtiva;

    public UrnaEletronica(Pleito pleito) {
        this.pleito = pleito;
        this.votosRegistrados = new ArrayList<>();
        this.eleicaoAtiva = true; // A urna já nasce pronta para receber votos
    }

    // Validador interno para não repetir código
    private void verificarStatusEleicao() {
        if (!eleicaoAtiva) {
            throw new EleicaoInativaException("A eleição já foi encerrada. Não é possível registrar novos votos.");
        }
    }

    @Override
    public void registrarVotoValido(int numeroCandidato, Cargo cargo) {
        verificarStatusEleicao();
        // Opcional: Aqui poderíamos validar se o candidato existe no Pleito
        // Se não existir, poderíamos converter automaticamente para NULO, como na urna real.
        Voto voto = new Voto(cargo, TipoVoto.VALIDO, numeroCandidato);
        votosRegistrados.add(voto);
    }

    @Override
    public void registrarVotoBranco(Cargo cargo) {
        verificarStatusEleicao();
        Voto voto = new Voto(cargo, TipoVoto.BRANCO, -1);
        votosRegistrados.add(voto);
    }

    @Override
    public void registrarVotoNulo(Cargo cargo) {
        verificarStatusEleicao();
        Voto voto = new Voto(cargo, TipoVoto.NULO, -1);
        votosRegistrados.add(voto);
    }

    @Override
    public void encerrarEleicao() {
        this.eleicaoAtiva = false;
        System.out.println("Atencao: Eleicao encerrada no sistema.");
    }

    // Método que será usado no Épico 4 para gerar a súmula
    public List<Voto> getVotosRegistrados() {
        return votosRegistrados;
    }
    
    public Pleito getPleito() {
        return pleito;
    }
}