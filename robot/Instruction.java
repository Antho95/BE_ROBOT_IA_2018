package robot;



import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import terrain.GrapheTerrain;
import terrain.Sommet;
import terrain.Triplet;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.robotics.pathfinding.DijkstraPathFinder;

public class Instruction {
	
	public final static int FORWARD = 0;
	public final static int RIGHT = 1;
	public final static int LEFT = 2;
	public final static int FOLLOW = 3;
	public final static int STOP = 4;
	public final static int TURN_BACK = 5;
	public final static int PAUSE = 8;
	public Travel travel;
	
	public Instruction(int speed){
		travel = new Travel(speed);
	}
	
	public void followInstruction(Integer instr){
		switch (instr.intValue()){
		case FORWARD : 
			travel.forward();
			break;
		case RIGHT :
			travel.right();
			break;
		case LEFT : 
			travel.left();
			break;
		case FOLLOW :
			travel.followLine();
			break;
		case STOP :
			travel.stop();
			break;
		case TURN_BACK :
			travel.turnBack();
			break;
		case PAUSE :
			travel.pause();
		}
	}
	
	public void followInstructionList (List<Integer> instructionList){
		for (Integer instr: instructionList){
			followInstruction(instr);
		}
	}
	
	public static void main(String[] args) {
		Instruction instrs = new Instruction(300);
		List<Integer> list = new ArrayList<Integer>();
		list.add(Integer.valueOf(FORWARD));
		list.add(Integer.valueOf(FOLLOW));
		list.add(Integer.valueOf(RIGHT));
		list.add(Integer.valueOf(FOLLOW));
		list.add(Integer.valueOf(LEFT));
		list.add(Integer.valueOf(FOLLOW));
		list.add(Integer.valueOf(RIGHT));
		list.add(Integer.valueOf(FOLLOW));
		list.add(Integer.valueOf(LEFT));
		list.add(Integer.valueOf(FOLLOW));
		list.add(Integer.valueOf(RIGHT));
		list.add(Integer.valueOf(FOLLOW));
		list.add(Integer.valueOf(LEFT));
		list.add(Integer.valueOf(FOLLOW));
		list.add(Integer.valueOf(RIGHT));
		list.add(Integer.valueOf(FOLLOW));
		list.add(Integer.valueOf(LEFT));
		list.add(Integer.valueOf(FOLLOW));
		list.add(Integer.valueOf(RIGHT));
		list.add(Integer.valueOf(FOLLOW));
		list.add(Integer.valueOf(LEFT));
		list.add(Integer.valueOf(FOLLOW));
		list.add(Integer.valueOf(RIGHT));
		list.add(Integer.valueOf(FOLLOW));
		list.add(Integer.valueOf(LEFT));
		list.add(Integer.valueOf(FOLLOW));
		list.add(Integer.valueOf(RIGHT));
		list.add(Integer.valueOf(FOLLOW));
		list.add(Integer.valueOf(LEFT));
		list.add(Integer.valueOf(FOLLOW));
		list.add(Integer.valueOf(RIGHT));
		list.add(Integer.valueOf(FOLLOW));
		list.add(Integer.valueOf(LEFT));
		list.add(Integer.valueOf(STOP));

		Button.waitForAnyPress();
		instrs.followInstructionList(list);
		
		List <Integer> list2 = new ArrayList<Integer>();
		list.add(FORWARD);
		list.add(FOLLOW);
		list.add(TURN_BACK);
		list.add(FORWARD);
		list.add(FOLLOW);
		list.add(STOP);
		Button.waitForAnyPress();
		instrs.followInstructionList(list2);
	}

	
}
