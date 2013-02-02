package edu.kit.curiosity.behaviors.colorGate;

import edu.kit.curiosity.Settings;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

/**
 * The class {@code FollowWall} describes the behavior to follow a wall.
 * 
 * @author Team Curiosity
 * 
 */
public class FollowWallColor implements Behavior {

	private boolean suppressed = false;

	private UltrasonicSensor sonic;
	private DifferentialPilot pilot;

	private int distanceToWall;

	/**
	 * Constructs a new FollowWall Behavior.
	 * 
	 * @param distance
	 *            - the distance in which the wall is followed.
	 */
	public FollowWallColor(int distance) {
		distanceToWall = distance;
		pilot = Settings.PILOT;
		sonic = Settings.SONIC;
	}

	/**
	 * The Behavior takes control if the robot is not in the swamp
	 */
	@Override
	public boolean takeControl() {

		return (Settings.onColors);
	}

	/**
	 * The robot moves along the Wall and turns to the right, if the wall is too
	 * far away.
	 */
	@Override
	public void action() {
		suppressed = false;
		if (Math.abs(Settings.LIGHT.getNormalizedLightValue() - Settings.searchedColor) < 20) {
			Settings.colorFound = true;
		}
		while (!suppressed) {
			if (sonic.getDistance() > (distanceToWall + 10)) {
				pilot.arc(-15, -90, true);
			} else if (sonic.getDistance() <= distanceToWall) {
				pilot.arc(40, 20, true);
			} else if (sonic.getDistance() > distanceToWall) {
				pilot.arc(-60, -20, true);
			}
		}
		pilot.stop();
	}

	/**
	 * Initiates the cleanup when this Behavior is suppressed
	 */
	@Override
	public void suppress() {
		suppressed = true;
	}

}
