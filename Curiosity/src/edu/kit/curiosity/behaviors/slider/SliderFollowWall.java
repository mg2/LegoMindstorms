package edu.kit.curiosity.behaviors.slider;

import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import edu.kit.curiosity.Settings;

/**
 * The class {@code FollowWall} describes the behavior to follow a wall.
 * 
 * @author Team Curiosity
 * 
 */
public class SliderFollowWall implements Behavior {

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
	public SliderFollowWall(int distance) {
		distanceToWall = distance;
		pilot = Settings.PILOT;
		sonic = Settings.SONIC;
	}

	/**
	 * The Behavior takes control if the robot is not in the swamp
	 */
	@Override
	public boolean takeControl() {

		return (!Settings.atStartofSlider && !Settings.afterSlider);
	}

	/**
	 * The robot moves along the Wall and turns to the right, if the wall is too
	 * far away.
	 */
	@Override
	public void action() {
		suppressed = false;
		Settings.motorAAngle = Settings.SENSOR_RIGHT;
		while (!suppressed
				&& !((Settings.TOUCH_L.isPressed() || Settings.TOUCH_R
						.isPressed())) && Settings.LIGHT.getLightValue() < 80) {
			if (sonic.getDistance() > (distanceToWall + 10)) {
				pilot.arc(-15, -90, true);
			} else if (sonic.getDistance() <= distanceToWall) {
				pilot.arc(40, 20, true);
			} else if (sonic.getDistance() > distanceToWall) {
				pilot.arc(-60, -20, true);
			}
		}
		if (Settings.LIGHT.getLightValue() >= 80) {
			pilot.stop();
			System.out.println("LINE Detected");
			Settings.afterSlider = true;
			Settings.motorAAngle = Settings.SENSOR_FRONT;
			Settings.PILOT.setTravelSpeed(Settings.PILOT.getMaxTravelSpeed() * 0.15);
		}
	}

	/**
	 * Initiates the cleanup when this Behavior is suppressed
	 */
	@Override
	public void suppress() {
		suppressed = true;
	}

}
