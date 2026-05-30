package exceptions;

// Exceção para quando o título digitado não estiver na lista (Mock) do Pleito
public class EleitorNaoCadastradoException extends Exception {
    public EleitorNaoCadastradoException(String mensagem) {
        super(mensagem);
    }
}

