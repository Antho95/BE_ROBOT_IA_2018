package movement;

import lejos.nxt.Button;
import lejos.nxt.LCD;
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
			while (!leftSensor.overThreshold()){
				pid.alterSpeed(rightSensor.getLight(),true);
				Thread.sleep(40);
			}
		}
		
		public void followUntilBlack() throws InterruptedException{
			while (leftSensor.overThreshold()){
				pid.alterSpeed(rightSensor.getLight(),true);
				Thread.sleep(40);
			}
		}
	}
	
	public void move(){
		ControlerTurnRight controler = new ControlerTurnRight();
		LCD.drawInt(1, 6, 6);
		controler.forwardUntilBlack();
		try {
			LCD.drawInt(2, 6, 6);
			controler.followUntilWhite();
			LCD.drawInt(3, 6, 6);
			controler.followUntilBlack();
			LCD.drawInt(4, 6, 6);
			controler.followUntilWhite();
			LCD.drawInt(5, 6, 6);
			controler.followUntilBlack();
			LCD.drawInt(6, 6, 6);
			controler.followUntilWhite();
			LCD.drawInt(7, 6, 6);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		TurnRight m = new TurnRight(300);
		FollowLine fl = new FollowLine(300);
		Button.waitForAnyPress();
		leftMotor.forward();
		rightMotor.forward();
		m.move();
		fl.move();
		m.move();
		m.stop();
	}
}
