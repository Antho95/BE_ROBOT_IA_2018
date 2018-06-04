package bluetooth;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import robot.Bipper;
import robot.Instruction;


import lejos.nxt.Button;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;

public class Connexion {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		boolean stop = false;
		
		while (!stop) {
			//Liste d'instruction a suivre
			Instruction inst = new Instruction(300);
		
			//Attente de la connection
			System.out.println("Waiting for Bluetooth connection...");
			BTConnection connection = Bluetooth.waitForConnection();
			//Connextion etablie
			//Entree-Sortie
			DataInputStream inputStream = connection.openDataInputStream();
			DataOutputStream outputStream = connection.openDataOutputStream();

			try {
				int input = 0;
				while (input != 99){
					List<Integer> list = new ArrayList<Integer>();
					input = inputStream.readInt();
					System.out.print(input);
					switch(input){
						//a Droite		
						case 1 : 	list.add(0);
									list.add(1);
									list.add(4);
									break;
						//a Gauche			
						case 2 : 	list.add(0);
									list.add(2);
									list.add(4);
									break;
						//Suivre Ligne			
						case 3 : 	
						case 6 :
						case 7 :
									list.add(0);
									list.add(3);
									list.add(4);
									break;
									
						//Demi-tour			
						case 5 : 	list.add(0);
									list.add(5);
									list.add(4);
									break;
						case 8 :	
									list.add(8);
									break;
						case 4 :	 list.add(4);
									 break;
						case 9 :	list.add(0);
									list.add(1);
									list.add(3);
									list.add(4);
									break;
						case 10:	list.add(0);
									list.add(2);
									list.add(3);
									list.add(4);
									break;
//						case 11 :	list.add(0);
//									list.add(1);
//									list.add(3);
//									list.add(5);
//									list.add(3);
//									list.add(0);
//									list.add(1);
//									list.add(4);
//									break;
//						case 12:	list.add(0);
//									list.add(2);
//									list.add(3);
//									list.add(5);
//									list.add(0);
//									list.add(3);
//									list.add(2);
//									list.add(4);
//									break;
						default :
					}
						inst.followInstructionList(list);
						try {
							if (input == 6){
								Bipper.victimeSound();
							}
							if (input == 7){
								Bipper.hopitalSound(inputStream.readInt());
							}
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				
					outputStream.writeInt(input);
					outputStream.flush();

				}
				if (input == 99){
					stop = true;
					break;
				}
			}
			catch (IOException e) {
				System.out.println("Exception: " + e.getClass());
				stop = true;
			}
		}
	}

}