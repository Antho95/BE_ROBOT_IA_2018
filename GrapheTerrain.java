package terrain;

import java.util.*;

public class GrapheTerrain {
		
	private List<Sommet> graphe = new ArrayList<Sommet>();
	
	//Recuperer le sommet dans un graphe
	public  Sommet getSommet(int id) {
		
		return graphe.get(id);
		
	}
	
	public int getNbSommet(){
		return graphe.size();
	}

	public void addArc(int source, int destination, boolean gauche, int inter) {
		
		//recuperation des sommets correspondant
		Sommet s = getSommet(source);
		Sommet d = getSommet(destination);
		
		//création des arcs
		Triplet t1 = new Triplet(d,gauche,d.getL(), inter);
		Triplet t2 = new Triplet(s,!gauche,s.getL(), inter);
		
		//memorisation dans les tableau d'adjacense de chaque sommet
		s.adjacent.add(t1);
		d.adjacent.add(t2);
		
	}
	
	public List<Sommet> getGraphe (){
		
		return graphe;
		
	}
	
	public static void main(String[] args) {
		
		
		int N = 12;//Nombre de sommet
		
		//Création du Graphe.
		GrapheTerrain graphe = new GrapheTerrain();
		
		//Graphe - Ajouts des Sommets.
		Sommet A = new Sommet(0,3);
		graphe.getGraphe().add(A);
		Sommet B = new Sommet(1,7.001);
		graphe.getGraphe().add(B);
		Sommet C = new Sommet(2,1.002);
		graphe.getGraphe().add(C);
		Sommet D = new Sommet(3,1.003);
		graphe.getGraphe().add(D);
		Sommet E = new Sommet(4,1.005);
		graphe.getGraphe().add(E);
		Sommet F = new Sommet(5,1.006);
		graphe.getGraphe().add(F);
		Sommet G = new Sommet(6,1.007);
		graphe.getGraphe().add(G);
		Sommet H = new Sommet(7,3.008);
		graphe.getGraphe().add(H);
		Sommet I = new Sommet(8,5.009);
		graphe.getGraphe().add(I);
		Sommet J = new Sommet(9,3.01);
		graphe.getGraphe().add(J);
		Sommet K = new Sommet(10,3.011);
		graphe.getGraphe().add(K);
		Sommet L = new Sommet(11,7.012);
		graphe.getGraphe().add(L);
		
		//Graphe - Ajout des Arcs.	
		boolean aGauche = true;
		boolean aDroite = false;

		graphe.addArc(0, 1, aDroite,1);
		graphe.addArc(0, 2, aGauche,1);
		graphe.addArc(0, 3, aDroite,2);
		graphe.addArc(0, 5, aGauche,2);

		graphe.addArc(1, 2, aDroite,1);
		graphe.addArc(1, 7, aGauche,6);
		graphe.addArc(1, 11, aDroite,6);
		
		graphe.addArc(2, 3, aGauche,3);
		graphe.addArc(2, 4, aDroite,3);
		
		graphe.addArc(3, 5, aDroite,2);
		graphe.addArc(3, 4, aGauche,3);
		
		graphe.addArc(4, 7, aDroite,4);
		graphe.addArc(4, 6, aGauche,4);
		
		graphe.addArc(5, 6, aDroite,5);
		graphe.addArc(5, 8, aGauche,5);
		
		graphe.addArc(6, 7, aGauche,4);
		graphe.addArc(6, 8, aDroite,5);
		
		graphe.addArc(7, 11, aGauche,6);
		
		graphe.addArc(8, 9, aDroite,8);
		graphe.addArc(8, 10, aGauche,8);
		
		graphe.addArc(9, 10, aGauche,7);
		graphe.addArc(9, 10, aDroite,8);
		graphe.addArc(9, 11, aDroite,7);
		
		graphe.addArc(10, 11, aGauche,7);
		
		//////////////////////////////////////////////////////////////////////////////////////////
		LinkedList<Integer> resultats = new LinkedList<Integer>();
		resultats = graphe.dijkstra( 8, 10, N);
		

		for(int i =0; i<resultats.size() ;i++) {
			System.out.print(" -> "+ resultats.get(i));
		}System.out.println();
		
		
		//////////////////////////////////////////////////////////////////////////////////////////

	}
	

	//Permet de trouver le sommet le plus proche d'un autre si il n'est pas visited
	public static int sommetLePlusProche (double[] distance, int[] visited, int N) {
		int somProche = -1;
		for(int i = 0 ; i < N; i++) {
			if (visited[i] != 1 && (somProche ==-1 || distance[i] < distance[somProche]))
				somProche = i;
		}
		return somProche;
	}
	//Permet de recuperer la direction
	public static Triplet getArcTriplet (Sommet s1,Sommet s2) {
		for (Triplet t : s1.adjacent)
			if(t.getPremier() == s2)
				return t;
		Triplet T = new Triplet(s1,false , 9999, 9999);
		return T;
	}
	
	//Permet la fin de dijkstra
	public static boolean tousVisited(int[] visited) {
		boolean res = true;
		for(int i =0; i< visited.length ; i++) {
			if(visited[i]==0)
				res = false;
		}
		return res;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////
	
	public LinkedList<Integer> dijkstra ( int depart, int arrivee, int N){
		
		
		double [][] matrix = new double [N][N];//Matrice pour appliquer algo de recherche
		
		double [] distance = new double [N];//Memoriser la distance de chaque chemin
		
		Triplet [] pred = new Triplet [N];//Memorisation du predecesseur
		LinkedList<Integer> intersection = new LinkedList<Integer>();
		
		int [] visited = new int [N];//Memorisation des sommets visited
		
		LinkedList<Integer> res = new LinkedList<Integer>();
		int sommetDepart = depart;
		int sommetArrive = arrivee;
		int dV = -1;
		
		//initialisation de la matrice - INFINI
				for(int i = 0; i<N ; i++){
					for(int j = 0; j<N ; j++){
						matrix[i][j] = 9999;
					}
				}

	

			visited[sommetDepart] = 1; //Sommet de depart - visited
			
			//Recuperation des distances des somAdjacent au sommet de depart
			for(int i=0;i<N;i++) {
				distance[i] = matrix[sommetDepart][i];
			}distance[sommetDepart] = 0;
			
			//Initialisation de la matrice avec les bonnes valeurs
			
			for (int i = 0; i<N ; i++) { //Pour chaque sommet
				
				pred[i] = getArcTriplet(getSommet(i), getSommet(sommetDepart)); //initialisation 
				visited[i] = 0; //initialisation a 0
				Sommet somVoisin = getSommet(i); //Sommet i
				
				for (Triplet t: somVoisin.adjacent) { //Pour ses sommets adjacents
					
					int idVoisin = (t.getPremier()).getS(); //Prend l'id du sommet adjacent correspondant
					matrix[i][idVoisin] = t.getTroisieme(); //Memorise le poid de l'arc i vers idVoisin
					
				}
			}
			
			//ALGO Dj
			
			visited[sommetDepart] = 1; //Sommet de depart - visited
			
			//Recuperation des distances des somAdjacent au sommet de depart
			for(int i=0;i<N;i++) {
				distance[i] = matrix[sommetDepart][i];
			}distance[sommetDepart] = 0;
	
			//DIJKSTRA 
			while(!tousVisited(visited)) {
				
				int somProche = sommetLePlusProche(distance, visited, N); // Recherche du sommet le plus proche
				Sommet sommetS = getSommet(somProche);
				visited[somProche] = 1;  // Sommet le plus proche - visited
				
				for(Triplet t : sommetS.adjacent) { // pour tout les sommet adjacent a somProche
					
					int j = t.getPremier().getS();//recuperation du numero de sommet voisin
					Sommet sommetD = t.getPremier();
					
					if(matrix[somProche][j] != 9999 && visited[j] != 1) { //si il existe un arc && n'est pas visited
						
						double newDist = distance[somProche] + matrix[somProche][j]; // distance au sommet destination
						
						if(newDist < distance[j]) {
							
							distance[j] = newDist; // Memorisation de la dist
							pred[j] = getArcTriplet(sommetD, sommetS); //getArcTriplet(sommetD,getSommet(j)); // Memorisation du pred
							
						}
					}
				}	
			}
			
			int k = -1 ;
			
			//////////////////////////////////////////////////////////////////////////////////////////
			
			//Avancer = 0; Gauche = 2; Droite = 1; SuivreLigne = 3; Stop = 4; DemiTour = 5; 
			int j=sommetArrive;
			do {
				k = j;
				j = pred[j].getPremier().getS();
				res.add(0,3);
				if (getArcTriplet(getSommet(j), getSommet(k)).getSecond())
					res.add(0,2);
				else 
					res.add(0,1);

				dV = getArcTriplet(getSommet(j), getSommet(k)).getQuatrieme();
				intersection.add(0,dV);
				
			}while (j != sommetDepart);
		
		res.add(0,3);
		res.add(0,0);
		res.add(4);
//		for(int i = 0; i< res.size();i++)
//			System.out.print(res.get(i));
//		System.out.println("\n");
//		for(int i = 0; i< intersection.size();i++)
//			System.out.print(intersection.get(i));
//		System.out.println();
		res.add(intersection.get(0));
		res.add(intersection.get(intersection.size()-1));
		res.add(Integer.valueOf((int)distance[arrivee]));
		return res;
	}

}
