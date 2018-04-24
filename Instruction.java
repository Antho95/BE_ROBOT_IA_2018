package robot;



import java.util.ArrayList;
import java.util.List;

import lejos.nxt.Button;

public class Instruction {
	
	private final static int FORWARD = 0;
	private final static int RIGHT = 1;
	private final static int LEFT = 2;
	private final static int FOLLOW = 3;
	private final static int STOP = 4;
	private Travel travel;
	
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
		}
	}
	
	public void followIntructionList (List<Integer> instructionList){
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
//		list.add(Integer.valueOf(FOLLOW));
//		list.add(Integer.valueOf(RIGHT));
//		list.add(Integer.valueOf(FOLLOW));
//		list.add(Integer.valueOf(LEFT));
//		list.add(Integer.valueOf(FOLLOW));
//		list.add(Integer.valueOf(RIGHT));
//		list.add(Integer.valueOf(FOLLOW));
//		list.add(Integer.valueOf(LEFT));
//		list.add(Integer.valueOf(FOLLOW));
//		list.add(Integer.valueOf(RIGHT));
//		list.add(Integer.valueOf(FOLLOW));
//		list.add(Integer.valueOf(LEFT));
//		list.add(Integer.valueOf(FOLLOW));
//		list.add(Integer.valueOf(RIGHT));
//		list.add(Integer.valueOf(FOLLOW));
//		list.add(Integer.valueOf(LEFT));
//		list.add(Integer.valueOf(FOLLOW));
//		list.add(Integer.valueOf(RIGHT));
//		list.add(Integer.valueOf(FOLLOW));
//		list.add(Integer.valueOf(LEFT));
//		list.add(Integer.valueOf(FOLLOW));
//		list.add(Integer.valueOf(RIGHT));
//		list.add(Integer.valueOf(FOLLOW));
//		list.add(Integer.valueOf(LEFT));
//		list.add(Integer.valueOf(FOLLOW));
//		list.add(Integer.valueOf(RIGHT));
//		list.add(Integer.valueOf(FOLLOW));
		list.add(Integer.valueOf(STOP));
		Button.waitForAnyPress();
		instrs.followIntructionList(list);
	}
}
