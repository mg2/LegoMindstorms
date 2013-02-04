package edu.kit.curiosity.behaviors.slider;

import lejos.nxt.TouchSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;
import edu.kit.curiosity.Settings;

/**
 * The class {@code RollFloor} describes the reaction to hits during the RollFloor.
 * @author Team Curiosity
 *
 */
public class AfterRollFloor implements Behavior {

	private boolean suppressed = false;

	private DifferentialPilot pilot;
	
	/**
	 * Constructs a new RollFloor Behavior
	 */
	public AfterRollFloor() {
		pilot = Settings.PILOT;
	}
	
	/**
	 * The Behavior takes control
	 * if  one of the {@link TouchSensor} is pressed.
	 */
	@Override
	public boolean takeControl() {
		return (Settings.SONIC.getDistance() < 30);
	}

	/**
	 * Rotates to the left if right TouchSensor is pressed,
	 * to the right if left.
	 */
	@Override
	public void action() {
		suppressed = false;
		Delay.msDelay(1000);
		if (Settings.SONIC.getDistance() < 30) {
			pilot.rotate(-136.5);
			while(!(Settings.TOUCH_L.isPressed() || Settings.TOUCH_R.isPressed())) {
				pilot.travel(10);
			}
			Delay.msDelay(1000);
			while(!(Settings.TOUCH_L.isPressed() || Settings.TOUCH_R.isPressed())) {
				pilot.travel(10);
			}
			pilot.rotate(-136.5);
			pilot.travel(-20);
			while (Settings.SONIC.getDistance() > 15) {
				pilot.travel(20);
				Delay.msDelay(1000);
			}
			
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
