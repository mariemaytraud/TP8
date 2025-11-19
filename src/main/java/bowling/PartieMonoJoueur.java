package bowling;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe a pour but d'enregistrer le nombre de quilles abattues lors des
 * lancers successifs d'<b>un seul et même</b> joueur, et de calculer le score
 * final de ce joueur
 */
public class PartieMonoJoueur {

	private final List<Tour> tours = new ArrayList<>();
	private Tour tourCourant;
	private static final int NOMBRE_DE_TOURS = 10;
	private static final int MAX_QUILLES = 10;
	private boolean estTerminee = false;

	/**
	 * Constructeur - crée une nouvelle partie avec le 1er tour
	 */
	public PartieMonoJoueur() {
		creerNouveauTour();
	}

	/**
	 * Cette méthode doit être appelée à chaque lancer de boule
	 *
	 * @param nombreDeQuillesAbattues le nombre de quilles abattues lors de ce lancer
	 * @throws IllegalStateException si la partie est terminée
	 * @return vrai si le joueur doit lancer à nouveau pour continuer son tour, faux
	 *         sinon
	 */
	public boolean enregistreLancer(int nombreDeQuillesAbattues) {
		if (estTerminee) {
			throw new IllegalStateException("La partie est terminée");
		}

		// Validation du nombre de quilles
		if (nombreDeQuillesAbattues < 0 || nombreDeQuillesAbattues > MAX_QUILLES) {
			throw new IllegalArgumentException("Le nombre de quilles doit être entre 0 et 10");
		}

		// Validation pour les tours non-finaux : les 2 lancers ne doivent pas dépasser 10
		if (tourCourant.getNumeroTour() < NOMBRE_DE_TOURS && tourCourant.getNombreDeLancers() == 1) {
			int quillesDuPremierLancer = tourCourant.getLancer(0).getNombreDeQuillesAbattues();
			if (!tourCourant.getLancer(0).estUnStrike()
					&& quillesDuPremierLancer + nombreDeQuillesAbattues > MAX_QUILLES) {
				throw new IllegalArgumentException(
						"Le total des quilles ne peut pas dépasser 10 dans un tour");
			}
		}

		// Enregistre le lancer
		tourCourant.ajouteLancer(new Lancer(nombreDeQuillesAbattues));

		// Vérifie si le tour doit continuer
		boolean leTourContinue = tourCourant.doitContinuer();

		if (!leTourContinue) {
			// Le tour est terminé
			tours.add(tourCourant);

			// Vérifie si la partie est terminée
			if (tourCourant.getNumeroTour() == NOMBRE_DE_TOURS) {
				estTerminee = true;
			} else {
				// Crée le prochain tour
				creerNouveauTour();
			}
		}

		return leTourContinue;
	}

	/**
	 * Cette méthode donne le score du joueur. Si la partie n'est pas terminée, on
	 * considère que les lancers restants abattent 0 quille.
	 * 
	 * @return Le score du joueur
	 */
	public int score() {
		// Crée une copie de la liste des tours pour le calcul
		List<Tour> toursTemp = new ArrayList<>(tours);

		// Ajoute le tour courant si une partie est en cours
		if (!estTerminee) {
			toursTemp.add(tourCourant);
		}

		// Complète avec des tours vides (0 quilles) jusqu'à 10 tours
		while (toursTemp.size() < NOMBRE_DE_TOURS) {
			Tour tourVide = new Tour(toursTemp.size() + 1);
			// Ajoute les lancers nécessaires pour que le tour soit valide
			if (toursTemp.size() + 1 < NOMBRE_DE_TOURS) {
				tourVide.ajouteLancer(new Lancer(0));
				tourVide.ajouteLancer(new Lancer(0));
			} else {
				// Dernier tour
				tourVide.ajouteLancer(new Lancer(0));
				tourVide.ajouteLancer(new Lancer(0));
				tourVide.ajouteLancer(new Lancer(0));
			}
			toursTemp.add(tourVide);
		}

		// Utilise le calculateur de score
		CalculateurScore calculateur = new CalculateurScore(toursTemp);
		return calculateur.calculScore();
	}

	/**
	 * @return vrai si la partie est terminée pour ce joueur, faux sinon
	 */
	public boolean estTerminee() {
		return estTerminee;
	}

	/**
	 * @return Le numéro du tour courant [1..10], ou 0 si le jeu est fini
	 */
	public int numeroTourCourant() {
		if (estTerminee) {
			return 0;
		}
		return tourCourant.getNumeroTour();
	}

	/**
	 * @return Le numéro du prochain lancer pour tour courant [1..3], ou 0 si le jeu
	 *         est fini
	 */
	public int numeroProchainLancer() {
		if (estTerminee) {
			return 0;
		}
		return tourCourant.getNombreDeLancers() + 1;
	}

	/**
	 * Crée un nouveau tour et le rend courant
	 */
	private void creerNouveauTour() {
		tourCourant = new Tour(tours.size() + 1);
	}

}
