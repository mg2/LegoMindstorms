package edu.kit.curiosity.behaviors.colorGate;
import edu.kit.curiosity.Settings;
import lejos.nxt.TouchSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

/**
 * This class describes the Behavior when hitting a Wall
 * @author Team Curiosity
 *
 */
public class HitWallColor implements Behavior {

	private boolean suppressed = false;
	
	private TouchSensor touch_r;
	private TouchSensor touch_l;
	private DifferentialPilot pilot;
	
	
	/**
	 * Constructs a new HitWall Behavior
	 */
	public HitWallColor() {
		touch_r = Settings.TOUCH_R;
		touch_l = Settings.TOUCH_L;
		pilot = Settings.PILOT;
	}
	
	/**
	 * This Behavior takes control if the TouchSensor is pressed.
	 */
	@Override
	public boolean takeControl() {
		return Settings.onColors && Settings.colorFound && (touch_r.isPressed() || touch_l.isPressed());
	}

	/**
	 * The robot turns left.
	 * 
	 */
	@Override
	public void action() {
		suppressed = false;
		pilot.travel(-5);
		pilot.rotate(135);
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
