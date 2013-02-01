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
		return (tsl.isPressed() || tsr.isPressed());
	}

	@Override
	public void action() {
		suppressed = false;
		Motor.A.rotateTo(Motor.A.getPosition() - 90);
		dp.travel(-10);
		dp.rotate(100);
		while (/*!suppressed && */ls.getLightValue() < 50) {
			if (!dp.isMoving() && us.getDistance() > (distanceToWall + 10)) {
				dp.arc(-15, -90, true);
			} else if (!dp.isMoving() && us.getDistance() < distanceToWall) {
				dp.arc(60, 20, true);
			} else if (!dp.isMoving() && us.getDistance() >= distanceToWall) {
				dp.arc(-60, -20, true);
			}
		}
		dp.travel(-5);
		Motor.A.rotateTo(Motor.A.getPosition() + 90);
		dp.stop();
		Motor.A.flt(true);
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}
