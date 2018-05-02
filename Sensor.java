package robot;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;

public class Sensor extends Thread {
	private LightSensor sensor;
	private int previousLight;
	private int lightValue;
	private boolean gauche;
	private int threshold;
	
	public Sensor(LightSensor sensor,int threshold, boolean gauche){
		this.sensor = sensor;
		this.gauche = gauche;
		this.threshold = threshold;
		this.lightValue = sensor.getLightValue();
		this.previousLight = sensor.getLightValue();
		start();
	}
	
	public void run(){
		while (!Button.ESCAPE.isPressed()){
			previousLight = lightValue;
			lightValue = sensor.getLightValue();
			if (gauche){
				LCD.drawInt(lightValue, 4, 1);
			}
			else{
				LCD.drawInt(lightValue, 1, 1);
			}
		}
	}
	
	public int getLight(){
		return lightValue;
	}
	
	public int getThreshold(){
		return threshold;
	}
	
	public boolean overThreshold(){
		return threshold < lightValue;
	}
	
	public boolean variation(){
		return ((overThreshold() && previousLight <= threshold) || (!overThreshold() && previousLight > threshold));
	}
}
