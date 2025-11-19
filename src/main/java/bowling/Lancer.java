package bowling;

/**
 * Représente un lancer de boule avec le nombre de quilles abattues
 */
public class Lancer {

	private final int nombreDeQuillesAbattues;

	/**
	 * Constructeur
	 * 
	 * @param nombreDeQuillesAbattues le nombre de quilles abattues (0-10)
	 */
	public Lancer(int nombreDeQuillesAbattues) {
		if (nombreDeQuillesAbattues < 0 || nombreDeQuillesAbattues > 10) {
			throw new IllegalArgumentException("Le nombre de quilles doit être entre 0 et 10");
		}
		this.nombreDeQuillesAbattues = nombreDeQuillesAbattues;
	}

	/**
	 * @return le nombre de quilles abattues
	 */
	public int getNombreDeQuillesAbattues() {
		return nombreDeQuillesAbattues;
	}

	/**
	 * @return true si c'est un strike (10 quilles)
	 */
	public boolean estUnStrike() {
		return nombreDeQuillesAbattues == 10;
	}

}
