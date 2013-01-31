package edu.kit.curiosity;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.Motor;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import edu.kit.curiosity.behaviors.*;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Button.ESCAPE.addButtonListener(new ButtonListener() {
		      public void buttonPressed(Button b) {
			        System.exit(0);
		      }

		      public void buttonReleased(Button b) {
		      }
		    });
	    Motor.A.setSpeed(Motor.A.getSpeed() / 5);
	    Motor.A.setStallThreshold(5, 500);
		Behavior b1 = new DriveForward();
	    Behavior b2 = new WallTooClose();
	    Behavior b3 = new WallTooFar();
	    Behavior b4 = new HitWall();
	    Behavior b5 = new MotorAStall();
	    Behavior [] bArray = {b1, b2, b3, b4, b5};
	    Arbitrator arby = new Arbitrator(bArray);
	    while(true) arby.start();
		}
	}
