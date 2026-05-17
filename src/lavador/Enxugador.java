package lavador;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

public class Enxugador implements Runnable {

	private static final Logger logger = Logger.getLogger(Enxugador.class.getName());

	private static final Random randGen = new Random();
	private static final int TEMPO_MIN = 3;
	private static final int TEMPO_MAX = 10;

	private static int contador;
	private int numSerie;

	private Escorredor escorredor;
	private AtomicBoolean trabalhando;

	public Enxugador(Escorredor escorredor, AtomicBoolean trabalhando) {
		this.escorredor = escorredor;
		this.trabalhando = trabalhando;

		this.numSerie = ++Enxugador.contador;
	}

	@Override
	public void run() {
		logger.fine(this + " iniciou...");

		Prato prato = null;

		while (trabalhando.get()) {

			try {
				prato = escorredor.retirar();
				enxugar(prato);
			} catch (Exception e) {
			}
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
