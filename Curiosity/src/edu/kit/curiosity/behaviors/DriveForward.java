package edu.kit.curiosity.behaviors;

import edu.kit.curiosity.Settings;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.*;

/**
 * This class describes the Behavior to simple drive forward.
 * @author Team Curiosity
 *
 */
public class DriveForward implements Behavior {
	private DifferentialPilot pilot;
	private boolean suppressed = false;
	private UltrasonicSensor sonic;
	
	/**
	 * Constructs a new DriveForward Behavior
	 */
	public DriveForward() {
		this.pilot = Settings.PILOT;
		pilot.setTravelSpeed(pilot.getTravelSpeed() / 5);
		sonic = (new UltrasonicSensor(SensorPort.S1));
	}

	/**
	 * Takes always Control.
	 * returns true
	 */
	public boolean takeControl() {
		return true;
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
		suppressed = false;
		pilot.forward();
		while (!suppressed) {
			System.out.println(sonic.getDistance() + " Drive");
			
			Thread.yield();
		}
		pilot.stop();
	}
}