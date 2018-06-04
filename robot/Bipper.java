package robot;

import lejos.nxt.Sound;

public class Bipper {
	
	public static void victimeSound() throws InterruptedException{
		Sound.beep();
		Thread.sleep(500);
	}
	
	public static void hopitalSound(int nbVictimes) throws InterruptedException{
		for (int i = 0; i < nbVictimes; ++i){
			Sound.twoBeeps();
			Thread.sleep(500);
		}
	}
	
	public static void main(String[] args) {
		Bipper b = new Bipper();
		try {
			b.victimeSound();
			b.hopitalSound(0);
			b.victimeSound();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
