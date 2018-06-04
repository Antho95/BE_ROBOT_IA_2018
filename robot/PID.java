package robot;


import lejos.nxt.Button;
import lejos.nxt.NXTRegulatedMotor;

import lejos.util.PIDController;

public class PID {
	private NXTRegulatedMotor motorP;
	private NXTRegulatedMotor motorS;
	private int initialSpeed;
	private int average = 40;
	private int Kp = 1200;//1200;//950
	private int Ki = 10;//10;//18
	private int Kd = 0;//100;//2650
	private int integral = 0;
	private int derivative = 0;
	private int lastError = 0;
	
	
	public PID(NXTRegulatedMotor motorP, NXTRegulatedMotor motorS,int initialSpeed){
		this.motorP = motorP;
		this.motorS = motorS;
		this.initialSpeed = initialSpeed;
		this.motorP.setSpeed(initialSpeed);
		this.motorS.setSpeed(initialSpeed);
	}
	
	public int doPID(int lightValue){
		int addSpeed;
		int error = lightValue - average;
		integral = integral + error;
//		if (integral > 10000)
//			integral = 10000;
//		if (integral < -10000){
//			integral = -10000;
//		}
		derivative = error - lastError;
		addSpeed = (Kp * error + Ki/10 * integral + Kd * derivative)/100;
		lastError = error;
		return addSpeed;
	}
	
	public void alterSpeed(int lightValue, boolean apply){
		int speedVariation = doPID(lightValue);
		if (apply){
			motorP.setSpeed(initialSpeed - speedVariation);
			motorS.setSpeed(initialSpeed + speedVariation);
		}
	}


	
}
