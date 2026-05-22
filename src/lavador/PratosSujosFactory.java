package lavador;

import java.util.Random;

public final class PratosSujosFactory {
	private static final Random randGen = new Random();
	private static int contador = 0;
	
	public static Prato getPratoSujo() {
		int randNum = randGen.nextInt(1, 11);
		NivelSujeira nivelSujeira = randNum <= 3 ? NivelSujeira.BAIXO : randNum <= 9 ? NivelSujeira.MEDIO : NivelSujeira.ENGORDURADO;
		contador++;
		
		return new Prato(contador, nivelSujeira);
	}
}
