package edu.kit.curiosity.behaviors;
import edu.kit.curiosity.Settings;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

/**
 * This class describes the Behavior when hitting a Wall
 * @author Team Curiosity
 *
 */
public class HitWall implements Behavior {

	private boolean suppressed = false;
	
	private TouchSensor touch;
	private UltrasonicSensor sonic;
	private DifferentialPilot pilot;
	
	
	/**
	 * Constructs a new HitWall Behavior
	 */
	public HitWall() {
		touch = new TouchSensor(Settings.TOUCH_SENSOR_PORT);
		sonic = new UltrasonicSensor(Settings.SONIC_SENSOR_PORT);
		pilot = Settings.pilot;
	}
	
	/**
	 * This Behavior takes control if the TouchSensor is pressed.
	 */
	@Override
	public boolean takeControl() {
		return touch.isPressed();
	}

	/**
	 * The robot turns 90° right if the right side is free,
	 * else it turns 90° left.
	 */
	@Override
	public void action() {
		suppressed = false;
		if (sonic.getDistance() > 15) {
			pilot.rotate(90, true);
		} else {
			pilot.rotate(-90, true);
		}
		
		while( pilot.isMoving() && !suppressed ) {
			Thread.yield();
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
