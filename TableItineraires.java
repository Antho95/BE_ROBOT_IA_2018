package tableItineraire;

import terrain.GrapheTerrain;

public class TableItineraires {
	
	private Itineraire[][] tableItineraire;
	
	public TableItineraires(GrapheTerrain gt){
		int nbSommet = gt.getNbSommet();
		tableItineraire = new Itineraire[nbSommet][nbSommet];
		for (int i = 0; i < nbSommet; ++i){
			for (int j = 0; j < nbSommet; ++j){
				if (i != j){
					tableItineraire[i][j] = new Itineraire(gt, i, j);
				}
			}
		}
	}
	
	public Itineraire getItineraire(int depart, int arrivee, int derIntersection){
		return tableItineraire[depart][arrivee].getItineraire(derIntersection);
	}
	
	public void afficherTable(){
		System.out.println("\nTable Itineraire :");
		for (int d = 0; d<tableItineraire.length; ++d){
			System.out.println("\n*Source "+d+" :");
			for (int a = 0; a<tableItineraire.length; ++a){
				if (a!=d){
					System.out.println("\n**Destination "+a+" :");
					System.out.println("***Instructions : "+tableItineraire[d][a].getInstructions());
				}
			}
		}
	}
	
}
