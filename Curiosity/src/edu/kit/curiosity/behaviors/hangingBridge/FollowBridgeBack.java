package edu.kit.curiosity.behaviors.hangingBridge;

import lejos.nxt.LightSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import edu.kit.curiosity.Settings;

public class FollowBridgeBack implements Behavior {
	
	private boolean suppressed = false;
	
	static DifferentialPilot pilot = Settings.PILOT;
	static LightSensor light = Settings.LIGHT;
	
	/**
	 * Light threshold.
	 */
	static final int blackWhiteThreshold = 40;

	/**
	 * Thread sleep time.
	 */
	static final int sleep = 10;


	@Override
	public boolean takeControl() {
		return true; // TODO
	}

	@Override
	public void action() {
		while (!suppressed) {
			if (light.getLightValue() > blackWhiteThreshold) {
				// On white, turn right
				// System.out.print("right ");
				//pilot.steer(-tr, 10, true);
				pilot.rotate(2);
				pilot.travel(-10);
			} else {
				// On black, turn left
				// System.out.print("left ");
				//pilot.steer(tr, -10, true);
				pilot.rotate(-2);
				pilot.travel(-10);
			}
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.print(light.getLightValue() + " ");
		}

	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}
