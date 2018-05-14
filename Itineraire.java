package tableItineraire;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import robot.Instruction;
import terrain.GrapheTerrain;

public class Itineraire {
	
	private int derniereInter;
	private List<Integer> instructions;
	private int distance;
	private int premierInter;
	
	public Itineraire(GrapheTerrain gt, int depart, int arrivee){
		List<Integer> list = gt.dijkstra(depart, arrivee, gt.getNbSommet());
		distance = list.remove(list.size()-1).intValue();
		derniereInter = list.remove(list.size()-1).intValue();
		premierInter = list.remove(list.size()-1).intValue();
		instructions = list;
	}
	
	public Itineraire(int premierInter, int derniereInter, List<Integer> instructions, int distance){
		this.derniereInter = derniereInter;
		if (instructions != null){
			this.instructions = new LinkedList<Integer>(instructions);
		}
		else{
			this.instructions = null;
		}
		this.distance = distance;
		this.premierInter = premierInter;
	}
	
	public static Itineraire itineraireSommetCourant(){
		Itineraire ret = new Itineraire();
		ret.distance = 9999;
		ret.derniereInter = -1;
		ret.instructions = new LinkedList<Integer>();
		ret.instructions.add(Instruction.STOP);
		return ret;
	}
	
	private Itineraire(){}
	
	public static Itineraire concatener(Itineraire i1, Itineraire i2){
		Itineraire ret = new Itineraire();
		ret.derniereInter = i2.derniereInter;
		ret.distance = i1.distance+i2.distance;
		ret.instructions = new LinkedList<Integer>();
		for (int i = 0; i < i1.instructions.size()-1;++i){
			ret.instructions.add(i1.instructions.get(i));
		}
		for (int i = 2; i < i2.instructions.size();++i){
			ret.instructions.add(i2.instructions.get(i));
		}
		return ret;
	}
	
	public static boolean inferieur(Itineraire i1, Itineraire i2){
		return i1.distance < i2.distance;
	}
	
	public Itineraire getItineraire(int derIntersection){
		if (derIntersection != premierInter)
			return this;
		else{
			Itineraire ret = new Itineraire(premierInter,this.derniereInter,instructions,distance);
			ret.instructions.add(2, 3);
			ret.instructions.add(2, 0);
			ret.instructions.add(2, 5);
			return ret;
		}
	}
	
	public List<Integer> getInstructions(){
		return instructions;
	}

	public int getDerniereInter() {
		return derniereInter;
	}
}
