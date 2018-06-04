

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;

import com.sun.org.apache.bcel.internal.generic.Instruction;
import com.sun.xml.internal.ws.api.pipe.NextAction;

import robot.Robot;
import terrain.GrapheTerrain;
import utils.CalculItineraire;

import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;

public class RandomConnection implements Runnable {

	private DataInputStream my_dataInput;
	private DataOutputStream my_dataOutput;
	private final NXTInfo my_nxt;
	private int indiceRobot;
	private GrapheTerrain graphe;
	private final static List<Integer> fileDAttente = new ArrayList<Integer>();
	private static int nbFinis = 0;

	public RandomConnection(NXTInfo _nxt, int indice) {
		my_nxt = _nxt;
		indiceRobot = indice;
	}
	
	public void setGraphe(GrapheTerrain graphe) {
		this.graphe = graphe;
	}
	
	private static void ajouteAttente(int indiceRobot){
		synchronized(fileDAttente){
			if (!fileDAttente.contains(indiceRobot))
				fileDAttente.add(indiceRobot);
		}
	}
	
	public static void initialiserAttente(int nbRobots){
		for (int i = 0; i<nbRobots; i++){
			fileDAttente.add(Integer.valueOf(i));
		}
	}
	
	public static boolean attenteVide(){
		return fileDAttente.isEmpty();
	}
	
	public static int getFirstAttente(){
		synchronized (fileDAttente) {
			if (!fileDAttente.isEmpty()){
				return fileDAttente.remove(0);
			}
			else{
				return -1;
			}
		}
	}

	public boolean connect(NXTComm _comm) throws NXTCommException {
		if (_comm.open(my_nxt)) {

			my_dataInput = new DataInputStream(_comm.getInputStream());
			my_dataOutput = new DataOutputStream(_comm.getOutputStream());
		}
		return isConnected();
	}
	
	public void envoyerInstruction(int instruction){
		try {
			my_dataOutput.writeInt(instruction);
			my_dataOutput.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean isConnected() {
		return my_dataOutput != null;
	}

//	public void run() {
//		int intr = 0;
//		Scanner scan = new Scanner(System.in);
//		try {
//			
//			while (intr != -1) {
//				intr = scan.nextInt();
//				System.out.println(intr);
//				my_dataOutput.writeInt(intr);
//				my_dataOutput.flush();
////				int answer = my_dataInput.readInt();
////				System.out.println(my_nxt.name + " returned " + answer);
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//	}
	
	public void run(){
		try {
			int answer = 0;
			while (answer  != 99){
				answer = my_dataInput.readInt();
				System.out.println(my_nxt.name + " returned " + answer);
				if (answer != 99 && answer != 9 && answer != 10){
					RandomConnection.ajouteAttente(indiceRobot);
				}
			}
			++nbFinis;
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static int getNbFinis(){
		return nbFinis;
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int N = 12;//Nombre de sommet
		//Création du Graphe.
		GrapheTerrain graphe = GrapheTerrain.initGraphe2();
		int capacite = 2;
		
		CalculItineraire ci = new CalculItineraire(graphe, capacite);
		List<Robot> robots = new LinkedList<Robot>();
		robots.add(new Robot(0, 8, 7, capacite));
		robots.add(new Robot(1,10,5, capacite));
		RandomConnection.initialiserAttente(robots.size());
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
		graphe.setHopitaux(hopitaux);
		graphe.setVictimes(victimes);
		System.out.println(graphe.isHopital(hopitaux.get(0)));
		System.out.println(graphe.isVictime(victimes.get(1)));
		System.out.println(graphe.isHopital(2));
		System.out.println(graphe.isVictime(2));
		
		robots = ci.meilleurParcours(victimes, hopitaux, robots);
		System.out.println("ok");
		try {
			System.in.read();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			NXTInfo[] nxts = {
					new NXTInfo(NXTCommFactory.BLUETOOTH, "Hydra","0016531C24E7" ),
//					new NXTInfo(NXTCommFactory.BLUETOOTH, "UC-GL","001653161388"),
//					new NXTInfo(NXTCommFactory.BLUETOOTH, "Albert","001653162292"),
//					new NXTInfo(NXTCommFactory.BLUETOOTH, "Gurren Lagann","00165318EB71" ),
					new NXTInfo(NXTCommFactory.BLUETOOTH, "NXT","0016531C15FC" ),
					};

			ArrayList<RandomConnection> connections = new ArrayList<RandomConnection>(nxts.length);
			int i = 0;
			for (NXTInfo nxt : nxts) {
				RandomConnection rc = new RandomConnection(nxt,i);
				rc.setGraphe(graphe);
				connections.add(rc);
				i++;
			}

			for (RandomConnection connection : connections) {
				NXTComm nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);
				connection.connect(nxtComm);
			}

			ArrayList<Thread> threads = new ArrayList<Thread>(nxts.length);

			for (RandomConnection connection : connections) {
				threads.add(new Thread(connection));
			}

			for (Thread thread : threads) {
				thread.start();
			}
			int nbFinis = 0;
			while (nbFinis != robots.size()){
				while (RandomConnection.attenteVide());
				System.out.println(RandomConnection.fileDAttente);
				int indiceRobot = RandomConnection.getFirstAttente();
				if (!robots.get(indiceRobot).instructionsFinis()){
					int instruction = robots.get(indiceRobot).getInstruction();
					int next;
					int s;
					boolean occupe = false;
					Robot gene = null;
					System.out.println("Switch "+instruction);
					switch (instruction){
					case 1 :
						s = graphe.getSommet(robots.get(indiceRobot).getPosition()).nextSommet(false, robots.get(indiceRobot).getDernierInter());
						next = graphe.getSommet(robots.get(indiceRobot).getPosition()).nextInter(robots.get(indiceRobot).getDernierInter());
						for (Robot r : robots){
							if (r.getIdentifiant()!=indiceRobot){
								System.out.println(indiceRobot+" "+r.getPosition()+" "+s);
								if (r.getPosition() == s){
									gene = r;
								}
								occupe = occupe || r.getPosition() == s;
							}
						}
						if (!occupe){
							robots.get(indiceRobot).setPosition(s);
							robots.get(indiceRobot).setDernierInter(next);
							connections.get(indiceRobot).envoyerInstruction(instruction);
							robots.get(indiceRobot).incrementNumInstr();
						}
						else{
							connections.get(indiceRobot).envoyerInstruction(robot.Instruction.STOP);
							if (gene.instructionsFinis()){
								connections.get(gene.getIdentifiant()).envoyerInstruction(9);
								gene.setPosition(graphe.getSommet(gene.getPosition()).nextSommet(false, gene.getDernierInter()));
								gene.setDernierInter(graphe.getSommet(gene.getPosition()).nextInter(gene.getDernierInter()));
								connections.get(indiceRobot).envoyerInstruction(robot.Instruction.PAUSE);
							}
						}
						break;
					case 2 :
						s = graphe.getSommet(robots.get(indiceRobot).getPosition()).nextSommet(true, robots.get(indiceRobot).getDernierInter());
						next = graphe.getSommet(robots.get(indiceRobot).getPosition()).nextInter(robots.get(indiceRobot).getDernierInter());
						for (Robot r : robots){
							if (r.getIdentifiant()!=indiceRobot){
								System.out.println(indiceRobot+" "+r.getPosition()+s);
								if (r.getPosition() == s){
									gene = r;
								}
								occupe = occupe || r.getPosition() == s;
							}
						}
						if (!occupe){
							robots.get(indiceRobot).setPosition(s);
							robots.get(indiceRobot).setDernierInter(next);
							connections.get(indiceRobot).envoyerInstruction(instruction);
							robots.get(indiceRobot).incrementNumInstr();
						}
						else{
							connections.get(indiceRobot).envoyerInstruction(robot.Instruction.STOP);
							if (gene.instructionsFinis()){
								connections.get(gene.getIdentifiant()).envoyerInstruction(10);
								gene.setPosition(graphe.getSommet(gene.getPosition()).nextSommet(true, gene.getDernierInter()));
								gene.setDernierInter(graphe.getSommet(gene.getPosition()).nextInter(gene.getDernierInter()));
								connections.get(indiceRobot).envoyerInstruction(robot.Instruction.PAUSE);
							}
						}
						break;
					case 3 :
						robots.get(indiceRobot).incrementNumInstr();
						System.out.println("vict"+graphe.isVictime(robots.get(indiceRobot).getPosition()));
						if (graphe.isVictime(robots.get(indiceRobot).getPosition()) && !robots.get(indiceRobot).plein() && robots.get(indiceRobot).isObjectif(robots.get(indiceRobot).getPosition())){
							robots.get(indiceRobot).incrementerNbPorte();
							graphe.sauverVictime(robots.get(indiceRobot).getPosition());
							connections.get(indiceRobot).envoyerInstruction(6);
						}else{
							System.out.println("hop"+graphe.isHopital(robots.get(indiceRobot).getPosition()));
							if(graphe.isHopital(robots.get(indiceRobot).getPosition()) && !robots.get(indiceRobot).vide()){
								connections.get(indiceRobot).envoyerInstruction(7);
								connections.get(indiceRobot).envoyerInstruction(robots.get(indiceRobot).getNbPorte());
								robots.get(indiceRobot).setZeroNbPorte();
							}
							else{
								connections.get(indiceRobot).envoyerInstruction(instruction);
							}
							
						}
						break;
					case 5:
						robots.get(indiceRobot).incrementNumInstr();
						robots.get(indiceRobot).setDernierInter(graphe.getSommet(robots.get(indiceRobot).getPosition()).nextInter(robots.get(indiceRobot).getDernierInter()));
						connections.get(indiceRobot).envoyerInstruction(instruction);
						break;
						default:
							connections.get(indiceRobot).envoyerInstruction(instruction);
							robots.get(indiceRobot).incrementNumInstr();
					}
				}else{
					++nbFinis;
				}
			}
			for (RandomConnection rc : connections){
				rc.envoyerInstruction(99);
			}
			for (Thread thread : threads) {
				try {
					thread.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		} catch (NXTCommException e) {
			e.printStackTrace();
		} finally {
		}

	}
}

