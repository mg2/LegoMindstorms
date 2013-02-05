package edu.kit.curiosity.behaviors.slider;
import lejos.nxt.TouchSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;
import edu.kit.curiosity.Settings;

/**
 * This class describes the Behavior when hitting a Wall
 * @author Team Curiosity
 *
 */
public class SliderHitWall implements Behavior {

	private boolean suppressed = false;
	
	private TouchSensor touch_r;
	private TouchSensor touch_l;
	private DifferentialPilot pilot;
	
	
	/**
	 * Constructs a new HitWall Behavior
	 */
	public SliderHitWall() {
		touch_r = Settings.TOUCH_R;
		touch_l = Settings.TOUCH_L;
		pilot = Settings.PILOT;
	}
	
	/**
	 * This Behavior takes control if the TouchSensor is pressed.
	 */
	@Override
	public boolean takeControl() {
		return (touch_r.isPressed() || touch_l.isPressed());
	}

	/**
	 * The robot turns left.
	 * 
	 */
	@Override
	public void action() {
		suppressed = false;
		Settings.atStartofSlider = false;
		pilot.travel(-5);
		Delay.msDelay(2000);
		pilot.travel(5);
		if (touch_r.isPressed() || touch_l.isPressed()) {
			pilot.travel(-5);
			pilot.rotate(100);
		}
		
		while( pilot.isMoving() && !suppressed );
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
