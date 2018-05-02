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
		PID lPid = new PID(leftMotor, rightMotor, initialSpeed);
		PID rPid = new PID(rightMotor, leftMotor, initialSpeed);
		forwardUntilBlack();
		boolean followLeft = true;
		while (!rightSensor.overThreshold() || !leftSensor.overThreshold()){
			if (followLeft)
				lPid.alterSpeed(leftSensor.getLight(),followLeft);
			else
				rPid.alterSpeed(rightSensor.getLight(),!followLeft);
			if(rightSensor.overThreshold())
				followLeft = false;
			else if (leftSensor.overThreshold())
				followLeft = true;
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
