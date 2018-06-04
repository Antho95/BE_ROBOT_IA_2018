package terrain;
import java.util.*;

public class Sommet {
	
	private int id;
	LinkedList<Triplet> adjacent = new LinkedList<Triplet>();
	private double longueur;
	
	public Sommet (int id, double poids) {
		this.id = id ;
		this.longueur = poids;
	}
	
	public int getS(){
		return id;
	}
	
	public double getL(){
		return longueur;
	}
	
	public LinkedList<Triplet> getAdjacent() {
		return adjacent;
	}
	
	public int nextInter(int derInter){
		int next = derInter;
		for (Triplet t : adjacent){
			if (t.getQuatrieme() != derInter){
				next = t.getQuatrieme();
				break;
			}
		}
		return next;
	}
	
	public int nextSommet(boolean gauche, int derInter){
		int next = id;
		for (Triplet t : adjacent){
			if(t.getSecond()==gauche && t.getQuatrieme()!=derInter){
				next = t.getPremier().id;
				break;
			}
		}
		return next;
	}
	
	
}