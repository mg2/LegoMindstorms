package edu.kit.curiosity;

import lejos.nxt.Button;
<<<<<<< HEAD
import lejos.nxt.ButtonListener;
=======
>>>>>>> 284155833a840d7b2ef71994632f244cf359a85e
import lejos.nxt.Motor;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import edu.kit.curiosity.behaviors.*;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
<<<<<<< HEAD
		Button.ESCAPE.addButtonListener(new ButtonListener() {
		      public void buttonPressed(Button b) {
			        System.exit(0);
		      }
=======
		
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
>>>>>>> 284155833a840d7b2ef71994632f244cf359a85e

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
