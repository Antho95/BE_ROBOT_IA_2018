package movement;

import lejos.nxt.Button;
import robot.PID;

public class FollowLine extends Movement{
	
	public final static int FOLLOWLINE = 1;
	
	public FollowLine(int initialSpeed){
		super(initialSpeed);
	}
	
	private void forwardUntilBlack(){
		rightMotor.setSpeed(initialSpeed/2 - (initialSpeed/2*23/100));
		leftMotor.setSpeed(initialSpeed/2);
		while(rightSensor.overThreshold());
		leftMotor.setSpeed(initialSpeed);
		rightMotor.setSpeed(initialSpeed);
	}
	
	public void move(){
		PID pid = new PID(leftMotor, rightMotor, initialSpeed);
		forwardUntilBlack();
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (!rightSensor.overThreshold() || !leftSensor.overThreshold()){
			pid.alterSpeed(leftSensor.getLight());
		}
	}
	
	public static void main(String[] args) {
		FollowLine m = new FollowLine(300);
		Button.waitForAnyPress();
		leftMotor.forward();
		rightMotor.forward();
		m.move();
		m.stop();
	}
}
