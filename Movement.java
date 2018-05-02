package movement;

import robot.Sensor;
import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;

public abstract class Movement {

	protected static final NXTRegulatedMotor rightMotor = Motor.B;
	protected static final NXTRegulatedMotor leftMotor = Motor.C;
	protected static final Sensor rightSensor = new Sensor(new LightSensor(
			SensorPort.S2), 45, false);
	protected static final Sensor leftSensor = new Sensor(new LightSensor(
			SensorPort.S3), 45, true);
	protected int initialSpeed;
	
	
		
	
	
	public Movement(int speed) {
		initialSpeed = speed;
	}

	protected class StopLeftMotor extends Thread {
		public void run() {
			leftMotor.stop();
		}
	}

	public void stop() {
		StopLeftMotor stopLeft = new StopLeftMotor();
		stopLeft.start();
		rightMotor.stop();
	}

	public void forward() {
		leftMotor.forward();
		rightMotor.forward();
	}

	public abstract void move();
}
