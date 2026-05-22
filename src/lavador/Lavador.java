package lavador;

import java.util.logging.Logger;

public class Lavador implements Runnable {

	private static final Logger logger = Logger.getLogger(Lavador.class.getName());

	private static final int TEMPO_BAIXO = 3;
	private static final int TEMPO_MEDIO = 5;
	private static final int TEMPO_ENGORDURADO = 10;

	private static int contador = 0;
	private int numSerie;

	private Escorredor escorredor;

	public Lavador(Escorredor escorredor) {
		this.escorredor = escorredor;

		numSerie = ++Lavador.contador;
	}

	@Override
	public void run() {
		logger.fine(this + " iniciou...");

		Prato prato = null;

		while (App.trabalhando) {
			prato = PratosSujosFactory.getPratoSujo();
			try {
				lavar(prato);
				escorredor.inserir(prato);
			} catch (ArrayIndexOutOfBoundsException e) {
				logger.severe("Limites do escorredor foram violados. Encerrando o programa.");
				System.exit(1);
			} catch (Exception e) {
				logger.severe("Ocorreu um erro. Encerrando o programa.");
				System.exit(1);
			}
		}

		logger.fine(this + " terminou!");
	}

	private void lavar(Prato prato) throws Exception {
		int tempoEspera = switch (prato.getNivelSujeira()) {
		case NivelSujeira.BAIXO -> TEMPO_BAIXO;
		case NivelSujeira.MEDIO -> TEMPO_MEDIO;
		case NivelSujeira.ENGORDURADO -> TEMPO_ENGORDURADO;
		};

		logger.fine(this + " lavando " + prato);

		Thread.sleep(tempoEspera);
	}

	@Override
	public String toString() {
		return "Lavador [" + numSerie + "]";
	}
}
