package movement;

import lejos.nxt.Button;

public class MovementTest {

	
	public static void main(String[] args){
		TurnRight r = new TurnRight(300);
		FollowLine fl = new FollowLine(300);
		TurnLeft l = new TurnLeft(300);
		Button.waitForAnyPress();
		r.forward();
		fl.move();
		r.stop();
		Button.waitForAnyPress();
		r.forward();
		r.move();
		r.stop();
		Button.waitForAnyPress();
		r.forward();
		fl.move();
		r.stop();
		Button.waitForAnyPress();
		r.forward();
		l.move();
		r.stop();
	}

}
