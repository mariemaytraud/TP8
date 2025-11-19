package bowling;

import java.util.List;

/**
 * Classe responsable du calcul du score du bowling
 */
public class CalculateurScore {

	private final List<Tour> tours;

	/**
	 * Constructeur
	 * 
	 * @param tours la liste des tours complétés
	 */
	public CalculateurScore(List<Tour> tours) {
		this.tours = tours;
	}

	/**
	 * Calcule le score total du jeu
	 * 
	 * @return le score total
	 */
	public int calculScore() {
		int scoreTotal = 0;

		for (int i = 0; i < tours.size(); i++) {
			scoreTotal += calculScoreTour(i);
		}

		return scoreTotal;
	}

	/**
	 * Calcule le score d'un tour donné (en tenant compte des bonus)
	 * 
	 * @param indexTour l'index du tour (0-basé)
	 * @return le score du tour avec ses bonus
	 */
	private int calculScoreTour(int indexTour) {
		Tour tour = tours.get(indexTour);

		// Le dernier tour se calcule différemment : pas de bonus, juste la somme
		if (tour.getNumeroTour() == 10) {
			return tour.getTotalQuillesAbattues();
		}

		// Tours 1-9 avec bonus
		if (tour.estUnStrike()) {
			// Strike: 10 + les 2 lancers suivants
			return 10 + getLancersBonus(indexTour, 2);
		}

		if (tour.estUnSpare()) {
			// Spare: 10 + le 1er lancer suivant
			return 10 + getLancersBonus(indexTour, 1);
		}

		// Pas de bonus: juste la somme des quilles
		return tour.getTotalQuillesAbattues();
	}

	/**
	 * Récupère le nombre de quilles des n prochains lancers
	 * 
	 * @param indexTourCourant l'index du tour courant
	 * @param nombreLancers le nombre de lancers à récupérer
	 * @return la somme des quilles des lancers suivants
	 */
	private int getLancersBonus(int indexTourCourant, int nombreLancers) {
		int quilles = 0;
		int lancersRecuperes = 0;

		for (int i = indexTourCourant + 1; i < tours.size() && lancersRecuperes < nombreLancers; i++) {
			Tour tourSuivant = tours.get(i);

			// Pour chaque lancer du tour suivant, jusqu'à ce qu'on ait ce qu'il faut
			for (int j = 0; j < tourSuivant.getNombreDeLancers() && lancersRecuperes < nombreLancers; j++) {
				quilles += tourSuivant.getLancer(j).getNombreDeQuillesAbattues();
				lancersRecuperes++;
			}
		}

		return quilles;
	}

}
