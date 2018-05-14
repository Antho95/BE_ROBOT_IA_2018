package utils;

import java.util.LinkedList;
import java.util.List;

import lejos.nxt.Button;

import robot.Instruction;
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
			meilleurItineraire = itin;
		}
		return meilleurItineraire;
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


		List<Integer> list;
		CalculItineraire ci = new CalculItineraire(graphe, 1);
		Instruction inst = new Instruction(300);
		List<Integer> victimes = new LinkedList<Integer>();
		victimes.add(Integer.valueOf(7));
		victimes.add(Integer.valueOf(10));
		List<Integer> hopitaux = new LinkedList<Integer>();
		hopitaux.add(Integer.valueOf(0));
		list = ci.getInstructionsMeilleurTrajet(8, 8, victimes, hopitaux);
		for (Integer instr : list){
			System.out.print(instr.intValue()+"  ");
		}
		System.out.println();
		Button.waitForAnyPress();
		inst.followInstructionList(list);
	}
}
