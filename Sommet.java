package Terrain;
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
}