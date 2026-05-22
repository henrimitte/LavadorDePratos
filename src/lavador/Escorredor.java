package lavador;

import java.util.logging.Logger;

public class Escorredor {

	private static final Logger logger = Logger.getLogger(Escorredor.class.getName());

	private int inicio = 0, fim = 0, ocupacao = 0, maxPratos;
	private Prato[] pratos;

	public Escorredor(int maxPratos) {
		this.maxPratos = maxPratos;
		pratos = new Prato[maxPratos];
	}

	public void inserir(Prato prato) throws Exception {
		synchronized (this) {

			while (isCheio() && App.trabalhando) this.wait();

			if (!App.trabalhando) return;

			pratos[fim] = prato;
			ocupacao++;
			fim++;
			fim %= maxPratos;

			logger.finer("Inserindo " + prato);

			if (ocupacao == 1) notifyAll();
			else if (isCheio()) {
				logger.info("Escorredor CHEIO. Pratos = " + ocupacao);
				notifyAll();
			}
		}
	}

	public Prato retirar() throws Exception {
		Prato prato = null;

		synchronized (this) {

			while (isVazio() && App.trabalhando) this.wait();

			if (!App.trabalhando) return null;

			prato = pratos[inicio];
			pratos[inicio] = null;
			ocupacao--;
			inicio++;
			inicio %= maxPratos;

			logger.finer("Retirando " + prato);

			if (ocupacao == maxPratos - 1) notifyAll();
			else if (isVazio()) {
				logger.info("Escorredor VAZIO. Pratos = " + ocupacao);
				notifyAll();
			}
		}

		return prato;
	}

	private boolean isVazio() {
		return ocupacao == 0;
	}

	private boolean isCheio() {
		return ocupacao == maxPratos;
	}
}
