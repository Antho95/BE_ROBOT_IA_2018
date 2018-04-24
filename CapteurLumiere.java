package robot;

import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;

public class CapteurLumiere {
	private double lumiere;
	private LightSensor capteur;
	private double seuil = 45;
	private CapteurThread threadCapteur;
	
	public CapteurLumiere (SensorPort p){
		capteur = new LightSensor(p);
		lumiere = capteur.getLightValue();
		threadCapteur = new CapteurThread();
		threadCapteur.start();
	}
	
	private class CapteurThread extends Thread {
    	public void run(){
    		while (true){
				lumiere = capteur.getLightValue();
    		}
    	}
    }
	
	public double getLight(){
		return (lumiere);
	}
	
	public boolean seuilDepasser(){
		return seuil < getLight();
	}
	
	public boolean seuilDepasser(double lumiere){
		return seuil < lumiere;
	}
	
	public boolean variation(double precedente){
		double courante = getLight();
		return ((seuil < precedente && seuil >= courante) || (seuil >= precedente && seuil < courante));
	}
	
}
	

