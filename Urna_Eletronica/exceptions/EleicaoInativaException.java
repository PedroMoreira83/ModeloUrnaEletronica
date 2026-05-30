package exceptions;
// Exceção para quando a eleição já foi encerrada e tentam votar
public class EleicaoInativaException extends RuntimeException {
    public EleicaoInativaException(String mensagem) {
        super(mensagem);
    }
}