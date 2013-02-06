package edu.kit.curiosity.behaviors.whip;

import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import edu.kit.curiosity.Settings;

public class WhipDriveForward implements Behavior {
	
	private DifferentialPilot pilot;
	private boolean suppressed;

	/**
	 * Constructs a new DriveForward Behavior
	 */
	public WhipDriveForward() {
		this.pilot = Settings.PILOT;
	}
	
	public boolean takeControl() {
		return (Settings.beforeWhip || !Settings.afterWhip);
	}
	/**
	 * Initiates the cleanup when this Behavior is suppressed
	 */
	public void suppress() {
		suppressed = true;
	}

	/**
	 * Moves forward as long as this Behavior is active
	 */
	public void action() {
		System.out.println("Drive Forward");
		suppressed = false;
		Settings.PILOT.setTravelSpeed(Settings.PILOT.getMaxTravelSpeed() * 0.1);
		//pilot.forward();
		while (!suppressed) {
			//if (!pilot.isMoving()) {
				pilot.travel(1, true);
			//}
		}
		pilot.stop();
		Settings.PILOT.setTravelSpeed(Settings.PILOT.getMaxTravelSpeed() * 0.4);
	}

}
