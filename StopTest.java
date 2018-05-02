package movement;

import lejos.nxt.Button;
import lejos.nxt.Motor;

public class StopTest {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		FollowLine fl = new FollowLine(300);
		Motor.C.forward();
		Motor.B.forward();
		Thread.sleep(2000);
		fl.stop();
		Button.waitForAnyPress();
		Motor.C.forward();
		Motor.B.forward();
		Thread.sleep(2000);
		fl.stop();
		
	}

}
