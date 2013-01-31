package edu.kit.curiosity;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import edu.kit.curiosity.behaviors.*;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		while(!Button.ESCAPE.isDown()) {
			Motor.A.setSpeed(Motor.A.getMaxSpeed()/5);
			Settings.PILOT.setRotateSpeed(Settings.PILOT.getMaxRotateSpeed()/5);
			
			Behavior b1 = new DriveForward();
		    Behavior b2 = new WallTooClose();
		    Behavior b3 = new WallTooFar();
		    Behavior b4 = new HitWall();
		    Behavior b5 = new SensorHeadPosition();
		    Behavior [] bArray = {b1, b2, b3, b4, b5};
		    Arbitrator arby = new Arbitrator(bArray);
		    
		    arby.start();
		}

	}

}
