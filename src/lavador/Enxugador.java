package lavador;

import java.util.Random;
import java.util.logging.Logger;

public class Enxugador implements Runnable {

	private static final Logger logger = Logger.getLogger(Enxugador.class.getName());

	private static final Random randGen = new Random();
	private static final int TEMPO_MIN = 3;
	private static final int TEMPO_MAX = 10;

	private static int contador;
	private int numSerie;

	private Escorredor escorredor;

	public Enxugador(Escorredor escorredor) {
		this.escorredor = escorredor;

		numSerie = ++Enxugador.contador;
	}

	@Override
	public void run() {
		logger.fine(this + " iniciou...");

		Prato prato = null;

		while (App.trabalhando) try {
			prato = escorredor.retirar();
			if (prato != null) enxugar(prato);
		} catch (LimiteVioladoException e) {
			logger.severe(e.getMessage() + ". O programa será encerrado.");
			System.exit(1);
		} catch (ArrayIndexOutOfBoundsException e) {
			logger.severe("Tentou inserir fora dos limites do buffer. Encerrando o programa.");
			System.exit(1);
		} catch (Exception e) {
			logger.severe("Ocorreu um erro. Encerrando o programa.");
			System.exit(1);
		}

		logger.fine(this + " terminou!");
	}

	public void enxugar(Prato prato) throws Exception {
		int tempoEspera = randGen.nextInt(TEMPO_MIN, TEMPO_MAX + 1);

		logger.fine(this + " enxugando " + prato);

		Thread.sleep(tempoEspera);
	}

	@Override
	public String toString() {
		return "Enxugador [" + numSerie + "]";
	}
}
