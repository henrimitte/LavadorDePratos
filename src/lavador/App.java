package lavador;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class App {

	static {
		String fileName = "logging.properties";
		String logFilePath = Paths.get("").toAbsolutePath().resolve(fileName).toString();
		System.out.println("Arquivo de configuração em: " + logFilePath);
		try {
			LogManager.getLogManager().readConfiguration(new FileInputStream(logFilePath));
		} catch (SecurityException | IOException e) {
			System.out.println("Não foi possível carregar o arquivo de configuração do log. Usando o padrão do Java.");
		}
	}

	private static final Logger logger = Logger.getLogger(App.class.getName());

	private static final int TEMPO_TRABALHANDO_MS = 1000;
	private static final int MAX_PRATOS = 10;
	private static final int QTD_LAVADORES = 1;
	private static final int QTD_ENXUGADORES = 1;

	private static Escorredor escorredor;
	private static Thread[] lavadores;
	private static Thread[] enxugadores;

	private static AtomicBoolean trabalhando;

	public static void main(String[] args) throws Exception {
		prepare();

		work();

		Thread.sleep(TEMPO_TRABALHANDO_MS);

		stop();
	}

	private static void prepare() {
		trabalhando = new AtomicBoolean(false);

		escorredor = new Escorredor(MAX_PRATOS, trabalhando);
		lavadores = new Thread[QTD_LAVADORES];
		enxugadores = new Thread[QTD_ENXUGADORES];

		for (int i = 0; i < QTD_LAVADORES; i++) lavadores[i] = new Thread(new Lavador(escorredor, trabalhando));

		for (int i = 0; i < QTD_ENXUGADORES; i++) enxugadores[i] = new Thread(new Enxugador(escorredor, trabalhando));
	}

	private static void work() {
		logger.info("Iniciando turno...");

		trabalhando.set(true);

		for (Thread lavador : lavadores) lavador.start();

		for (Thread enxugador : enxugadores) enxugador.start();

	}

	private static void stop() throws Exception {
		trabalhando.set(false);

		synchronized (escorredor) {
			escorredor.notifyAll();
		}

		for (Thread lavador : lavadores) lavador.join();

		for (Thread enxugador : enxugadores) enxugador.join();

		logger.info("Turno finalizado!");
	}

}
