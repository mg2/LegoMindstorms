package edu.kit.curiosity.behaviors.tape;

import edu.kit.curiosity.Settings;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class ObstacleFound implements Behavior {

	private boolean suppressed = false;
	TouchSensor tsl = Settings.TOUCH_L;
	TouchSensor tsr = Settings.TOUCH_R;
	DifferentialPilot dp = Settings.PILOT;
	UltrasonicSensor us = Settings.SONIC;
	LightSensor ls = Settings.LIGHT;

	private final int distanceToWall = 12;

	@Override
	public boolean takeControl() {
		return (Settings.obstacle || tsl.isPressed() || tsr.isPressed());
	}

	@Override
	public void action() {
		suppressed = false;
		if (!Settings.obstacle) { //If not in obstacle mode - initialize obstacle mode
			Settings.obstacle = true;
			Settings.motorAAngle = 0;
			dp.travel(-10, true); // TODO true?
			dp.rotate(100, true); // TODO true?
		}
		while (!suppressed && ls.getLightValue() < 50) { //arcs until line found
			if (!dp.isMoving() && us.getDistance() > (distanceToWall + 10)) {
				dp.arc(-15, -90, true);
			} else if (!dp.isMoving() && us.getDistance() < distanceToWall) {
				dp.arc(60, 20, true);
			} else if (!dp.isMoving() && us.getDistance() >= distanceToWall) {
				dp.arc(-60, -20, true);
			}
		}
		if (ls.getLightValue() > 50) { //if line found - leave obstacle mode
			Settings.obstacle = false;
			dp.travel(-5);
			Settings.motorAAngle = 90;
		}
		dp.stop();
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}
