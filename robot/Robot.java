package robot;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import tableItineraire.Itineraire;

public class Robot {
	
	private int identifiant;
	private int position;
	private int dernierInter;
	private Itineraire itineraire;
	private int numInstruction;
	private int nbPorte;
	private int capacite;
	private List<Integer> objectifs;
	
	public Robot(int id, int position, int dernierInter, int capacite){
		this.identifiant = id;
		this.position = position;
		this.dernierInter = dernierInter;
		this.capacite = capacite;
		objectifs = new LinkedList<Integer>();
		List<Integer> inst = new LinkedList<Integer>();
		inst.add(Instruction.FORWARD);
		inst.add(Instruction.FOLLOW);
		inst.add(Instruction.STOP);
		this.itineraire = new Itineraire(dernierInter, dernierInter, inst , 0, position);
		this.numInstruction = 0;
		this.nbPorte = 0;
	}
	
	public Robot(Robot r){
		this.identifiant = r.identifiant;
		this.position = r.position;
		this.dernierInter = r.dernierInter;
		this.capacite = r.capacite;
		this.objectifs = new LinkedList<Integer>(r.objectifs);
		this.itineraire = new Itineraire(r.itineraire.getPremierInter(), r.itineraire.getDerniereInter(), r.itineraire.getInstructions(), r.itineraire.getDistance(),r.itineraire.getDepart());
		this.numInstruction = r.numInstruction;
		this.nbPorte = r.nbPorte;
	}
	
	public Robot(Robot r,Itineraire itin){
		this.identifiant = r.identifiant;
		this.position = r.position;
		this.dernierInter = r.dernierInter;
		this.capacite = r.capacite;
		this.objectifs = new LinkedList<Integer>(r.objectifs);
		this.itineraire = Itineraire.concatener(r.itineraire, itin);
		this.numInstruction = r.numInstruction;
		this.nbPorte = r.nbPorte;
	}
	
	public Robot(){
		itineraire = new Itineraire(-1, -1, null, 9999);
	}
	
	public boolean plein(){
		return nbPorte == capacite;
	}
	
	public boolean vide(){
		return nbPorte == 0;
	}
	
	public boolean isObjectif(int position){
		return objectifs.contains(position);
	}
	
	public void addObjectif(int position){
		objectifs.add(position);
	}
	
	public int getIdentifiant() {
		return identifiant;
	}
	
	public int getNbPorte() {
		return nbPorte;
	}
	
	public Itineraire getItineraire() {
		return itineraire;
	}
	
	public int getInstruction() {
		return itineraire.getInstructions().get(numInstruction);
	}
	
	public void setInstructions(List<Integer> instrs){
		itineraire.setInstructions(instrs);
	}
	
	public int getPosition() {
		return position;
	}
	
	public int getDernierInter() {
		return dernierInter;
	}
	
	
	public static int getDistanceMax(List<Robot> robots){
		int max = 0;
		for (Robot r : robots){
			if (max < r.itineraire.getDistance())
				max = r.itineraire.getDistance();
		}
		return max;
	}

	public static boolean inferieur(List<Robot> robots1, List<Robot> robots2) {
		int dist1 = 0;
		int dist2 = 0;
		int max1 = 0;
		int max2 = 0;
		for (Robot r : robots1){
			if (max1 < r.itineraire.getDistance())
				max1 = r.itineraire.getDistance();
			dist1 += r.itineraire.getDistance();
		}
		for (Robot r : robots2){
			if (max2 < r.itineraire.getDistance())
				max2 = r.itineraire.getDistance();
			dist2 += r.itineraire.getDistance();
		}
		if (max1 != max2)
			return max1 < max2;
		else
			return dist1 < dist2;
		
	}
	
	public void incrementerNbPorte(){
		++nbPorte;
	}
	
	public void setZeroNbPorte(){
		nbPorte = 0;
	}
	
	public void setDernierInter(int dernierInter) {
		this.dernierInter = dernierInter;
	}
	
	public void setPosition(int position) {
		this.position = position;
	}
	
	public void incrementNumInstr() {
		++numInstruction;
	}

	public boolean instructionsFinis() {
		return numInstruction == itineraire.getInstructions().size();
	}
	
	public void setNumInstr(int num){
		numInstruction = num;
	}
	
	public int getNumInstruction() {
		return numInstruction;
	}
}
