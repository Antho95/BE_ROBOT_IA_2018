package movement;

import lejos.nxt.Button;

public class MovementTest {

	
	public static void main(String[] args){
		TurnRight r = new TurnRight(200);
		FollowLine fl = new FollowLine(200);
		TurnLeft l = new TurnLeft(200);
		Button.waitForAnyPress();
		r.forward();
		fl.move();
//		r.stop();
//		Button.waitForAnyPress();
//		r.forward();
		r.move();
//		r.stop();
//		Button.waitForAnyPress();
//		r.forward();
		fl.move();
//		r.stop();
//		Button.waitForAnyPress();
//		r.forward();
		l.move();
		r.stop();
	}

}
