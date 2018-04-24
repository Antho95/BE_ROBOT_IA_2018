package Terrain;

import java.util.*;

public class GrapheTerrain {
		
	private static HashMap<Integer,Sommet> graphe = new HashMap<Integer,Sommet>();
	
	//Recuperer le sommet dans un graphe
	public static Sommet getSommet(int id) {
		
		return graphe.get(id);
		
	}

	public void addArc(int source, int destination, boolean gauche) {
		
		//recuperation des sommets correspondant
		Sommet s = getSommet(source);
		Sommet d = getSommet(destination);
		
		//création des arcs
		Triplet t1 = new Triplet(d,gauche,d.getL());
		Triplet t2 = new Triplet(s,!gauche,s.getL());
		
		//memorisation dans les tableau d'adjacense de chaque sommet
		s.adjacent.add(t1);
		d.adjacent.add(t2);
		
	}
	
	public HashMap<Integer,Sommet> getGraphe (){
		
		return graphe;
		
	}
	
	public static void main(String[] args) {
		
		
		int N = 6;//Nombre de sommet
		
		int sommetDepart = 0; //Sommet de depart 0 <= sommetDepart < N
		
		double [][] matrix = new double [N][N];//Matrice pour appliquer algo de recherche
		
		double [] distance = new double [N];//Memoriser la distance de chaque chemin
		
		Triplet [] pred = new Triplet [N];//Memorisation du predecesseur
		
		int [] visited = new int [N];//Memorisation des sommets visited
		
		//Variables auxiliaires
		double poids = 50; //poids test
		
		
		//initialisation de la matrice - INFINI
		for(int i = 0; i<N ; i++){
			for(int j = 0; j<N ; j++){
				matrix[i][j] = 9999;
			}
		}
		
		//Création du Graphe.
		GrapheTerrain graphe = new GrapheTerrain();
		
		//Graphe - Ajouts des Sommets.
		Sommet A = new Sommet(0,poids);
		graphe.getGraphe().put(0,A);
		Sommet B = new Sommet(1,poids+1);
		graphe.getGraphe().put(1,B);
		Sommet C = new Sommet(2,poids+2);
		graphe.getGraphe().put(2,C);
		Sommet D = new Sommet(3,poids+3);
		graphe.getGraphe().put(3,D);
		Sommet E = new Sommet(4,poids+4);
		graphe.getGraphe().put(4,E);
		Sommet F = new Sommet(5,poids+5);
		graphe.getGraphe().put(5,F);
//		Sommet G = new Sommet(6,poids+6);
//		graphe.getGraphe().put(6,G);
		
		//Graphe - Ajout des Arcs.	
		boolean aGauche = true;
		boolean aDroite = false;

		graphe.addArc(0, 1, aDroite);
		graphe.addArc(0, 2, aGauche);
		graphe.addArc(0, 3, aDroite);
		graphe.addArc(0, 5, aGauche);

		graphe.addArc(1, 2, aDroite);
		graphe.addArc(1, 4, aGauche);
		graphe.addArc(1, 5, aDroite);
		
		graphe.addArc(2, 3, aGauche);
		graphe.addArc(2, 4, aDroite);
		
		graphe.addArc(5, 3, aGauche);
		graphe.addArc(5, 4, aDroite);

		
		//Initialisation de la matrice avec les bonnes valeurs
		
		for (int i = 0; i<N ; i++) { //Pour chaque sommet
			
			pred[i] = tripletSommetProche(getSommet(i), getSommet(sommetDepart)); //initialisation 
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
		for(int i = 0; i < N-1 ; i++) { // Pour chaque sommet
			int somProche = sommetLePlusProche(distance, visited, N); // Recherche du sommet le plus proche
			Sommet sommetS = getSommet(i);
			Sommet sommetD = getSommet(somProche);
			Triplet tripletDest = tripletSommetProche(sommetS, sommetD);
			visited[somProche] = 1;  // Sommet le plus proche - visited
			for(int j = 0 ; j< N; j++) { // pour tout les sommet
				if(matrix[somProche][j] != 9999 && visited[j] != 1) { //si il n'est pas visited
					double dist = distance[somProche] + matrix[somProche][j]; // distance au sommet destination
					if(dist <= distance[j]) {
						distance[j] = dist; // Memorisation de la dist
						pred[j] = tripletDest; // Memorisation du pred
					}
				}
			}	
		}

		//Affichage des resultats
		
		//Affichage des distances a chaques sommets
		
		System.out.println(" Distances de " + numtochar(sommetDepart) + " a chaque sommet : ");
		for (int i =0; i<N ;i++) {
			System.out.print(" | "+ distance [i]);
		}System.out.println(" | ");
		System.out.println();
		
		//Affichage des chemins a chaques sommets
		System.out.println(" Chemins de " + numtochar(sommetDepart) + " a chaque sommet : ");

		for (int i =0; i<N ;i++) {
			
			if (i != sommetDepart) {
				
				String s = numtochar(i);
				System.out.print(" Chemin " + s );
				int j=i;
				do {
					j = pred[j].getPremier().getS();
					System.out.print( " <- " + numtochar(j) +" "+ tripletSommetProche(getSommet(j), getSommet(i)).getSecond() );
	
				}while (j != sommetDepart);
			System.out.println();	
			}
		}
		System.out.println();

//		//Affichage des Sommet :: -> SommetAdjacent du graphe
		
//		System.out.println("Sommet :: Sommets adjacents :");
//
//		for(int i =0; i<N ;i++) {
//			Sommet som = getSommet(i); {
//				System.out.print( i +" ::");
//			}
//			for (Triplet t : som.adjacent)  {
//				System.out.print(i + " -> "+ t.getPremier().getS() + "; ");
//			}System.out.println();
//		}
//		System.out.println();
		
		//Affichage de la matrice avec les poids ou l'on applique dj
		
//		System.out.println("Matrix d'adjacence avec poids (9999 = infini) :");
//
//		for(int i =0; i<N ;i++) {
//			for(int i2 =0; i2<N ;i2++) {
//				System.out.print(" | "+ matrix[i][i2]);
//			}
//			System.out.println(" | ");
//		}
//		System.out.println();
		
		//Affichage des preds
		
		System.out.println("Affichage des predecesseurs : ");
		for(int i = 0; i < N ; i++) {
			System.out.print(" :: ("+ pred[i].getPremier().getS() + " , " + pred[i].getSecond() + " , " + pred[i].getTroisieme() + ")" );
		}System.out.println();

//		//Affichage des visited
		
//		System.out.println("Affichage des visited : ");
//		for(int i =0; i<N ;i++) {
//			System.out.print(" -> "+ visited[i]);
//		}System.out.println();

	}
	
	//Permet de transfomer les int au char majuscule associe (0->A)
	public static String numtochar (int i) {
		if (i<25 && i>-1) 
			return String.valueOf((char)(i+65));
		else 
			return null;
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
	public static Triplet tripletSommetProche (Sommet s1,Sommet s2) {
		for (Triplet t : s1.adjacent)
			if(t.getPremier() == s2)
				return t;
		Triplet T = new Triplet(s1,false , 9999);
		return T;
	}

}
