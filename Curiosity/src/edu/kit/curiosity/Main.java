package edu.kit.curiosity;

import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import edu.kit.curiosity.behaviors.*;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Behavior b1 = new DriveForward();
	    Behavior b2 = new WallToClose();
	    Behavior b3 = new WallToFar();
	    Behavior b4 = new HitWall();
	    Behavior [] bArray = {b1, b2, b3, b4};
	    Arbitrator arby = new Arbitrator(bArray);
	    arby.start();

	}

}
