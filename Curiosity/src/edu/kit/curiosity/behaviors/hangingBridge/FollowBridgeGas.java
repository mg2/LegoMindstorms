package edu.kit.curiosity.behaviors.hangingBridge;

import edu.kit.curiosity.Settings;
import lejos.nxt.LightSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class FollowBridgeGas implements Behavior {
	
	private boolean suppressed = false;
	private boolean timesUp = false;
	
	static DifferentialPilot pilot = Settings.PILOT;
	static LightSensor light = Settings.LIGHT;
	
	/**
	 * Light threshold.
	 */
	static final int blackWhiteThreshold = 20;

	/**
	 * Thread sleep time.
	 */
	static final int sleep = 10;

	/**
	 * Turn rate.
	 */
	private static int tr = 45; // TODO not tested, previous was 90

	@Override
	public boolean takeControl() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void action() {
		suppressed = false;
		
		long curTime = System.currentTimeMillis();

		while (!suppressed && !timesUp) {
			if (System.currentTimeMillis() - curTime > 6000) {
				timesUp = true;
				Settings.travelBack = false;
				Settings.goBack = true;
			}
			if (light.getLightValue() > blackWhiteThreshold) {
				// On white, turn right
				// System.out.print("right ");
				pilot.steer(-tr, -10, true);
			} else {
				// On black, turn left
				// System.out.print("left ");
				pilot.steer(tr, 10, true);
			}
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (!suppressed) pilot.setTravelSpeed(pilot.getMaxTravelSpeed());
		while (!suppressed) {
			// TODO am Wand am Ende der Bruecke sich orientieren
			pilot.travel(10, true);
		}
		if (!suppressed) pilot.setTravelSpeed(pilot.getMaxTravelSpeed() * 0.15);
	}

	@Override
	public void suppress() {
		suppressed = true;

	}

}
