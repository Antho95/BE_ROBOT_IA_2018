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
			SensorPort.S1), 42, false);
	protected static final Sensor leftSensor = new Sensor(new LightSensor(
			SensorPort.S2), 42, true);
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
	
	public void pause(){
		stop();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void forward() {
		leftMotor.forward();
		rightMotor.forward();
	}

	public abstract void move();
}
