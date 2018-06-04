package utils;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import lejos.nxt.Button;

import robot.Instruction;
import robot.Robot;
import tableItineraire.Itineraire;
import tableItineraire.TableItineraires;
import terrain.GrapheTerrain;
import terrain.Sommet;

public class CalculItineraire {
	
	private TableItineraires tableItineraires;
	private int capacite;
	
	public CalculItineraire(GrapheTerrain gt,int capacite){
		tableItineraires = new TableItineraires(gt);
		this.capacite = capacite;
	}
	
	private List<Integer> victimesRestantes(List<Integer> victimes, int victimeSecourue){
		List<Integer> ret = new LinkedList<Integer>(victimes);
		ret.remove(Integer.valueOf(victimeSecourue));
		return ret;
	}
	
	private Itineraire meilleurParcours(int depart, List<Integer> victimes,List<Integer> hopitaux, int nbPorte, Itineraire itin){
		Itineraire meilleurItineraire;
		if (victimes.size()>0 || nbPorte>0){
			meilleurItineraire = new Itineraire(-1,-1,null,9999);
			if (nbPorte<capacite){
				for (Integer victimePos : victimes) {
					Itineraire tmp = meilleurParcours(victimePos.intValue(),victimesRestantes(victimes, victimePos),hopitaux,nbPorte+1,Itineraire.concatener(itin,tableItineraires.getItineraire(depart, victimePos, itin.getDerniereInter())));
					if (Itineraire.inferieur(tmp, meilleurItineraire)){
						meilleurItineraire = tmp;
					}
				}
			}
			if (nbPorte>0){
				for (Integer hopitalPos : hopitaux) {
					Itineraire tmp = meilleurParcours(hopitalPos.intValue(), victimes, hopitaux, 0,Itineraire.concatener(itin,tableItineraires.getItineraire(depart, hopitalPos, itin.getDerniereInter())));
					if (Itineraire.inferieur(tmp, meilleurItineraire)){
						meilleurItineraire = tmp;
					}
				}
			}
		}
		else{
//			System.out.println(itin.getInstructions());
			meilleurItineraire = itin;
		}
		return meilleurItineraire;
	}
	
	public List<Robot> meilleurParcours(List<Integer> victimes,List<Integer> hopitaux,List<Robot> robots){
		List<Robot> meilleurEquipe = null;
		//parcours de tous les robots
		for (int indiceRobot = 0; indiceRobot < robots.size(); ++indiceRobot){
			//verification de si il reste des victimes ou si le robot porte des victimes
			if (victimes.size()>0 || robots.get(indiceRobot).getNbPorte()>0){
				if (meilleurEquipe == null){
					//initialisation du la meilleur equipe si elle n'est pas deja initialisee
					meilleurEquipe = new ArrayList<Robot>();
					for (int i = 0; i < robots.size(); ++i){
						meilleurEquipe.add(new Robot());
					}
				}
				if (robots.get(indiceRobot).getNbPorte()<capacite){
					//parcours des victimes si le nombre de victime portee est inferieur a la capacite 
					for (Integer victimePos : victimes) {
						//initialisation de la nouvelle liste de robot
						List<Robot> tmp = new LinkedList<Robot>();
						for (int i = 0; i< robots.size(); ++i){
							if (i == indiceRobot){
								//modification du robot qui se deplace
								Itineraire itin;
								if (robots.get(indiceRobot).getItineraire().getDepart() != victimePos.intValue()){
									//si le robot n'est pas sur une victime
									//on recupere le chemin vers la victime depuis sa position
									itin = tableItineraires.getItineraire(robots.get(indiceRobot).getItineraire().getDepart(), victimePos, robots.get(indiceRobot).getItineraire().getDerniereInter());
								}
								else{
									List<Integer> inst = new LinkedList<Integer>();
									inst.add(Instruction.FORWARD);
									inst.add(Instruction.FOLLOW);
									inst.add(Instruction.STOP);
									itin = new Itineraire(robots.get(indiceRobot).getItineraire().getPremierInter(), robots.get(indiceRobot).getItineraire().getDerniereInter(), inst, 0);
								}
								tmp.add(new Robot(robots.get(i), itin));
								tmp.get(indiceRobot).getItineraire().setDepart(victimePos);
							}
							else{
								tmp.add(new Robot(robots.get(i)));
							}
						}
						tmp.get(indiceRobot).incrementerNbPorte();
						tmp.get(indiceRobot).addObjectif(victimePos);
						if (Robot.getDistanceMax(tmp)<=Robot.getDistanceMax(meilleurEquipe)){
							tmp = meilleurParcours(victimesRestantes(victimes, victimePos),hopitaux,tmp);
							if (Robot.inferieur(tmp, meilleurEquipe)){
								meilleurEquipe = tmp;
							}
						}
					}
				}
				if (robots.get(indiceRobot).getNbPorte()>0){
					for (Integer hopitalPos : hopitaux) {
						List<Robot> tmp = new LinkedList<Robot>();
						for (int i = 0; i< robots.size(); ++i){
							if (i == indiceRobot){
								Itineraire itin;
								if (robots.get(indiceRobot).getItineraire().getDepart() != hopitalPos.intValue()){
									itin = tableItineraires.getItineraire(robots.get(indiceRobot).getItineraire().getDepart(), hopitalPos, robots.get(indiceRobot).getItineraire().getDerniereInter());
								}
								else{
									List<Integer> inst = new LinkedList<Integer>();
									inst.add(Instruction.FORWARD);
									inst.add(Instruction.FOLLOW);
									inst.add(Instruction.STOP);
									itin = new Itineraire(robots.get(indiceRobot).getItineraire().getPremierInter(), robots.get(indiceRobot).getItineraire().getDerniereInter(), inst, 0);
								}
								tmp.add(new Robot(robots.get(i), itin));
								tmp.get(indiceRobot).getItineraire().setDepart(hopitalPos);
							}
							else{
								tmp.add(new Robot(robots.get(i)));
							}
						}
						tmp.get(indiceRobot).setZeroNbPorte();
						if (Robot.getDistanceMax(tmp)<=Robot.getDistanceMax(meilleurEquipe)){
							tmp = meilleurParcours(victimes,hopitaux,tmp);
							if (Robot.inferieur(tmp, meilleurEquipe)){
								meilleurEquipe = tmp;
							}
						}
					}
				}
			}
		}
		
		if (meilleurEquipe == null){
			meilleurEquipe = robots;
		}
		return meilleurEquipe;
	}
	
	public List<Integer> getInstructionsMeilleurTrajet(int depart, int derniereInter, List<Integer> victimes,List<Integer> hopitaux){
		List<Integer> instr = new LinkedList<Integer>();
		instr.add(Integer.valueOf(Instruction.FORWARD));
		instr.add(Integer.valueOf(Instruction.FOLLOW));
		instr.add(Integer.valueOf(Instruction.STOP));
		return meilleurParcours(depart, victimes, hopitaux, 0, new Itineraire(-1,derniereInter, instr, 0)).getInstructions();
	}
	
	
	public static void main(String[] args) {
		int N = 12;//Nombre de sommet
		
		//Création du Graphe.
		GrapheTerrain graphe = GrapheTerrain.initGraphe2();
		int capacite = 1;

//		Button.waitForAnyPress();
		List<Integer> list;
		CalculItineraire ci = new CalculItineraire(graphe, capacite);
//		Instruction inst = new Instruction(300);
		List<Robot> robots = new LinkedList<Robot>();
		robots.add(new Robot(0, 8, 3,capacite));
		robots.add(new Robot(1,10,6,capacite));
//		robots.add(new Robot(0,8,8));
//		robots.add(new Robot(1,11,7));
		List<Integer> victimes = new LinkedList<Integer>();
		victimes.add(Integer.valueOf(3));
		victimes.add(Integer.valueOf(4));
		victimes.add(Integer.valueOf(5));
		victimes.add(Integer.valueOf(11));
		victimes.add(Integer.valueOf(0));
//		victimes.add(Integer.valueOf(7));
//		victimes.add(Integer.valueOf(0));
//		victimes.add(Integer.valueOf(11));
		List<Integer> hopitaux = new LinkedList<Integer>();
		hopitaux.add(Integer.valueOf(2));
		hopitaux.add(Integer.valueOf(6));
		hopitaux.add(Integer.valueOf(9));
//		hopitaux.add(Integer.valueOf(1));
//		hopitaux.add(Integer.valueOf(9));
		list = ci.getInstructionsMeilleurTrajet(8, 3, victimes, hopitaux);
		for (Integer instr : list){
			System.out.print(instr.intValue()+"  ");
		}
		System.out.println();
		robots = ci.meilleurParcours(victimes, hopitaux, robots);
		for (Robot r : robots){
			for (Integer instr : r.getItineraire().getInstructions()){
				System.out.print(instr.intValue()+"  ");
			}
			System.out.println();
		}
//		inst.followInstructionList(list);
//		ci.tableItineraires.afficherTable();
	}
}
