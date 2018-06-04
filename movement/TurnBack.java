package movement;

import lejos.nxt.Button;
import lejos.nxt.Motor;

public class TurnBack extends Movement{

	public TurnBack(int speed) {
		super(speed);
	}

	@Override
	public void move() {
		int cpt = 0;
			leftMotor.setSpeed(initialSpeed);
			rightMotor.setSpeed(initialSpeed);
			leftMotor.backward();
			rightMotor.backward();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			stop();
			leftMotor.backward();
			rightMotor.forward();
			while (leftSensor.overThreshold());
			while (!leftSensor.overThreshold());
			while (leftSensor.overThreshold());
	}
	
	public static void main(String[] args) {
		TurnBack tb = new TurnBack(200);
		Button.waitForAnyPress();
		tb.move();
		tb.stop();
	}
	
}
