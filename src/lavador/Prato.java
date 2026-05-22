package lavador;

public final class Prato {
	private final int numSerie;
	private final NivelSujeira nivelSujeira;

	public Prato(int numSerie, NivelSujeira nivelSujeira) {
		this.numSerie = numSerie;
		this.nivelSujeira = nivelSujeira;
	}

	public NivelSujeira getNivelSujeira() {
		return nivelSujeira;
	}

	@Override
	public String toString() {
		return "Prato " + numSerie + " [" + nivelSujeira + "]";
	}

}
