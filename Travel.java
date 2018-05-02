package robot;

import movement.FollowLine;
import movement.Straight;
import movement.TurnLeft;
import movement.TurnRight;

public class Travel {
	private TurnLeft left;
	private TurnRight right;
	private FollowLine follow;
	private Straight straight;
	
	public Travel(int speed){
		left = new TurnLeft(speed);
		right = new TurnRight(speed);
		follow = new FollowLine(speed);
	}
	
	public void right(){
		right.move();
	}
	
	public void left(){
		left.move();
	}
	
	public void followLine(){
		follow.move();
	}
	
	public void stop(){
		follow.stop();
	}
	
	public void forward(){
		follow.forward();
	}
}
