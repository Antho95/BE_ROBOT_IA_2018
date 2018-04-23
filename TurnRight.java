package movement;

import lejos.nxt.Button;
import robot.PID;

public class TurnRight extends Movement{
	
	public TurnRight(int speed){
		super(speed);
	}
	
	private class ControlerTurnRight{
		
		public PID pid = new PID(rightMotor, leftMotor, initialSpeed);
		
		public void forwardUntilBlack(){
			rightMotor.setSpeed(initialSpeed/2);
			leftMotor.setSpeed(initialSpeed/2 - (initialSpeed/2*23/100));
			while(leftSensor.overThreshold());
			rightMotor.setSpeed(initialSpeed);
			leftMotor.setSpeed(initialSpeed);
		}
		
		public  void followUntilWhite() throws InterruptedException{
			Thread.sleep(50);
			while (!leftSensor.overThreshold()){
				pid.alterSpeed(rightSensor.getLight());
			}
		}
		
		public void followUntilBlack() throws InterruptedException{
			Thread.sleep(50);
			while (leftSensor.overThreshold()){
				pid.alterSpeed(rightSensor.getLight());
			}
		}
	}
	
	public void move(){
		ControlerTurnRight controler = new ControlerTurnRight();
		controler.forwardUntilBlack();
		try {
			controler.followUntilWhite();
			controler.followUntilBlack();
			controler.followUntilWhite();
			controler.followUntilBlack();
			controler.followUntilWhite();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		TurnRight m = new TurnRight(391);
		FollowLine fl = new FollowLine(391);
		Button.waitForAnyPress();
		leftMotor.forward();
		rightMotor.forward();
		m.move();
		fl.move();
		m.move();
		m.stop();
	}
}
