package lavador;

import java.util.Random;

public final class Prato {
	private static final Random randGen = new Random();
	private static int contador = 0;

	private final int numSerie;
	private final NivelSujeira nivelSujeira;

	private Prato(int numSerie, NivelSujeira nivelSujeira) {
		this.numSerie = numSerie;
		this.nivelSujeira = nivelSujeira;
	}

	public static Prato getInstance() {
		int randNum = randGen.nextInt(1, 11);

		NivelSujeira nivelSujeiraAleatorio = randNum <= 3 ? NivelSujeira.BAIXO
				: randNum <= 9 ? NivelSujeira.MEDIO : NivelSujeira.ENGORDURADO;

		return new Prato(++contador, nivelSujeiraAleatorio);
	}

	public NivelSujeira getNivelSujeira() {
		return nivelSujeira;
	}

	@Override
	public String toString() {
		return "Prato " + numSerie + " [" + nivelSujeira + "]";
	}

}
