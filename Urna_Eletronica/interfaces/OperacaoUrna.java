package interfaces;

import models.Cargo;

public interface OperacaoUrna {
    void registrarVotoValido(int numeroCandidato, Cargo cargo);
    void registrarVotoBranco(Cargo cargo);
    void registrarVotoNulo(Cargo cargo);
    void encerrarEleicao();
}