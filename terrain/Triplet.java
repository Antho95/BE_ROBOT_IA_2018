package terrain;

public class Triplet {
	
	private Sommet sommet;
	private boolean gauche;
	private double distance;
	private int inter;
	
	//Triplet pour pouvoir recuperer (une destination, savoir si il faut tourner au gauche et la distance
	public Triplet (Sommet s , boolean estAGauche, double d, int numInter) {
		sommet = s;
		gauche = estAGauche;
		distance = d;
		inter = numInter;
	}
	//Recuperer le sommet du triplet
	public Sommet getPremier() {
		return sommet;
	}
	//Savoir si c'est a gauche
	public boolean getSecond() {
		return gauche;
	}
	//Distance a parcourir (heuristique)
	public double getTroisieme() {
		return distance;
	}
	//Num d'intersection
	public int getQuatrieme() {
		return inter;
	}
}