package edu.kit.curiosity.sensortests;
import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;


public class RotateTest {
	private static DifferentialPilot dp;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		dp = new DifferentialPilot(8.5, 15.25, Motor.C, Motor.B);
		double travelSpeed = dp.getTravelSpeed();
		double rotateSpeed = dp.getRotateSpeed();
		configDiffPilot(rotateSpeed / 7, travelSpeed / 7);
		System.out.println("Waiting for button.");
		Button.ESCAPE.addButtonListener(new ButtonListener() {
	      public void buttonPressed(Button b) {
		        System.exit(0);
	      }

	      public void buttonReleased(Button b) {
	      }
	    });
		Button.LEFT.addButtonListener(new ButtonListener() {
		      public void buttonPressed(Button b) {
		      }

		      public void buttonReleased(Button b) {
		    	  dp.rotate(180);
		      }
		    });
		Button.RIGHT.addButtonListener(new ButtonListener() {
		      public void buttonPressed(Button b) {
		      }

		      public void buttonReleased(Button b) {
		    	  dp.rotate(-180);
		      }
		    });
		while(true);
	}
	private static void configDiffPilot(double d, double e) {
		dp.setRotateSpeed(d);
		dp.setTravelSpeed(e);
	}

}
