package movement;

import lejos.nxt.Button;
import robot.PID;

public class TurnLeft extends Movement{
	
	public TurnLeft(int speed){
		super(speed);
	}
	
	private class ControlerTurnLeft{
		
		public PID pid = new PID(leftMotor, rightMotor, initialSpeed);
		
		public void forwardUntilBlack(){
			rightMotor.setSpeed(initialSpeed/2 - (initialSpeed/2*23/100));
			leftMotor.setSpeed(initialSpeed/2);
			while(rightSensor.overThreshold());
			rightMotor.setSpeed(initialSpeed);
			leftMotor.setSpeed(initialSpeed);
		}
		
		public  void followUntilWhite() throws InterruptedException{
			Thread.sleep(50);
			while (!rightSensor.overThreshold()){
				pid.alterSpeed(leftSensor.getLight());
			}
		}
		
		public void followUntilBlack() throws InterruptedException{
			Thread.sleep(50);
			while (rightSensor.overThreshold()){
				pid.alterSpeed(leftSensor.getLight());
			}
		}
	}
	
	public void move(){
		ControlerTurnLeft controler = new ControlerTurnLeft();
		controler.forwardUntilBlack();
		try {
			controler.followUntilBlack();
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
		TurnLeft m = new TurnLeft(391);
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
