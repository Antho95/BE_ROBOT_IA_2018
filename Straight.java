package movement;

import robot.PID;

public class Straight extends Movement{
	
	public Straight(int speed){
		super(speed);
	}
	
	private class ControlerStraight{
		
		private PID rightPID = new PID(rightMotor, leftMotor, initialSpeed);
		private PID leftPID = new PID(leftMotor, rightMotor, initialSpeed);
		
		public void forwardUntilBlack(){
			while (leftSensor.overThreshold()){
				rightMotor.setSpeed(initialSpeed/2 - 50);
				leftMotor.setSpeed(initialSpeed/2);
				while(rightSensor.overThreshold());
				rightMotor.setSpeed(initialSpeed);
				leftMotor.setSpeed(initialSpeed);
			}
		}
		
		public void followRightBorderUntilWhite(){
			while (!leftSensor.overThreshold()){
				rightPID.alterSpeed(rightSensor.getLight());
			}
		}
		
		public void followRightBorderUntilBlack(){
			while (leftSensor.overThreshold()){
				rightPID.alterSpeed(rightSensor.getLight());
			}
		}
		
		public void followleftBorderUntilWhite(){
			while (!rightSensor.overThreshold()){
				leftPID.alterSpeed(leftSensor.getLight());
			}
		}
		
		public void followLeftBorderUntilBlack(){
			while (rightSensor.overThreshold()){
				leftPID.alterSpeed(leftSensor.getLight());
			}
		}
	}
	
	public void move(){
		ControlerStraight controler = new ControlerStraight();
	}
}
