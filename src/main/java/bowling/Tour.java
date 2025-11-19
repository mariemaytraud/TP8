package bowling;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente un tour de bowling. Un tour peut contenir de 1 à 3 lancers selon
 * les circonstances.
 */
public class Tour {

	private final int numeroTour;
	private final List<Lancer> lancers = new ArrayList<>();
	private static final int MAX_QUILLES = 10;

	/**
	 * Constructeur
	 * 
	 * @param numeroTour le numéro du tour (1-10)
	 */
	public Tour(int numeroTour) {
		if (numeroTour < 1 || numeroTour > 10) {
			throw new IllegalArgumentException("Le numéro du tour doit être entre 1 et 10");
		}
		this.numeroTour = numeroTour;
	}

	/**
	 * Ajoute un lancer au tour
	 * 
	 * @param lancer le lancer à ajouter
	 */
	public void ajouteLancer(Lancer lancer) {
		lancers.add(lancer);
	}

	/**
	 * Indique si le tour doit continuer (le joueur doit faire un deuxième ou
	 * troisième lancer)
	 * 
	 * @return true si le tour doit continuer, false sinon
	 */
	public boolean doitContinuer() {
		if (lancers.isEmpty()) {
			return true; // Le premier lancer doit toujours être fait
		}

		// Dernier tour : jusqu'à 3 lancers
		if (numeroTour == 10) {
			if (lancers.size() == 1) {
				// Après le 1er lancer, on continue toujours
				return true;
			}
			if (lancers.size() == 2) {
				// Après le 2e lancer, on continue si strike ou spare
				return estUnStrike() || estUnSpare();
			}
			// Après 3 lancers, le tour est fini
			return false;
		}

		// Tours 1-9
		if (lancers.size() == 1) {
			// Si strike au 1er lancer, le tour est fini
			if (lancers.get(0).estUnStrike()) {
				return false;
			}
			// Sinon, on doit faire un 2e lancer
			return true;
		}

		// Après 2 lancers, le tour est toujours fini pour les tours 1-9
		return false;
	}

	/**
	 * @return true si le tour est un strike
	 */
	public boolean estUnStrike() {
		return !lancers.isEmpty() && lancers.get(0).estUnStrike();
	}

	/**
	 * @return true si le tour est un spare
	 */
	public boolean estUnSpare() {
		if (lancers.size() < 2) {
			return false;
		}
		// Spare sauf si le 1er lancer est un strike
		return !lancers.get(0).estUnStrike()
				&& lancers.get(0).getNombreDeQuillesAbattues()
						+ lancers.get(1).getNombreDeQuillesAbattues() == MAX_QUILLES;
	}

	/**
	 * @return le nombre de lancers du tour
	 */
	public int getNombreDeLancers() {
		return lancers.size();
	}

	/**
	 * @param index l'index du lancer (0-basé)
	 * @return le lancer à l'index
	 */
	public Lancer getLancer(int index) {
		return lancers.get(index);
	}

	/**
	 * @return le numéro du tour
	 */
	public int getNumeroTour() {
		return numeroTour;
	}

	/**
	 * @return la somme des quilles abattues dans ce tour
	 */
	public int getTotalQuillesAbattues() {
		return lancers.stream().mapToInt(Lancer::getNombreDeQuillesAbattues).sum();
	}

	/**
	 * Indique si le tour est terminé
	 * 
	 * @return true si le tour est terminé, false sinon
	 */
	public boolean estTermine() {
		return !doitContinuer();
	}

}
