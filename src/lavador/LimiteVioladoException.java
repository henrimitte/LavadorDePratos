package lavador;

public class LimiteVioladoException extends RuntimeException {

	private static final long serialVersionUID = 5045149797415923741L;

	public LimiteVioladoException(String mensagem) {
		super(mensagem);
	}

	public LimiteVioladoException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}

}
