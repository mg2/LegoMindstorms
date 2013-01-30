package edu.kit.curiosity.behaviors;
import edu.kit.curiosity.Settings;
import lejos.nxt.TouchSensor;
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
	private DifferentialPilot pilot;
	
	
	/**
	 * Constructs a new HitWall Behavior
	 */
	public HitWall() {
		touch = Settings.TOUCH;
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
		while (touch.isPressed()) {
			pilot.travel(-1);
			pilot.rotate(90);
			/*pilot.backward();
			pilot.arcForward(25);*/
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
