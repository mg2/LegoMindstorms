package edu.kit.curiosity.behaviors.bridge;

import edu.kit.curiosity.Settings;
import lejos.nxt.TouchSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class HitWallBeforeBridge implements Behavior {
	private boolean suppressed = false;

	private TouchSensor touch_r;
	private TouchSensor touch_l;
	private DifferentialPilot pilot;

	public HitWallBeforeBridge() {
		touch_r = Settings.TOUCH_R;
		touch_l = Settings.TOUCH_L;
		pilot = Settings.PILOT;
	}

	@Override
	public boolean takeControl() {
		return (touch_r.isPressed() || touch_l.isPressed());
	}

	@Override
	public void action() {
		suppressed = false;
		pilot.rotate(-45);
		while (pilot.isMoving() && !suppressed);
		pilot.stop();

	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}
