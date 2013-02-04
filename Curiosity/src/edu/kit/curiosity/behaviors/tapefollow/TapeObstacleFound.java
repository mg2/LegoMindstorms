package edu.kit.curiosity.behaviors.tapefollow;

import edu.kit.curiosity.Settings;
import lejos.nxt.LightSensor;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;

public class TapeObstacleFound implements Behavior {

	private boolean suppressed = false;
	TouchSensor touch_l = Settings.TOUCH_L;
	TouchSensor touch_r = Settings.TOUCH_R;
	DifferentialPilot pilot = Settings.PILOT;
	UltrasonicSensor sensor = Settings.SONIC;
	LightSensor light = Settings.LIGHT;

	private final int distanceToWall = 10;


	@Override
	public boolean takeControl() {
		return (Settings.obstacle || touch_l.isPressed() || touch_r.isPressed());
	}

	@Override
	public void action() {
		suppressed = false;
		//If not in obstacle mode - initialize.		
		//If obstacle ON the line.
		if (!Settings.obstacle && Settings.SONIC.getDistance() < 20) {
			// Goes into obstacle mode.
			Settings.obstacle = true;

			pilot.steer(-50, 30, false);
			Delay.msDelay(1000);

			// If opponent and not a plant.
			if (Settings.SONIC.getDistance() > 40) {
				Settings.obstacle = false;
			}
			// If opponent/plant still there.
			else {
				Settings.motorAAngle = -90;
				pilot.rotate(80);
				pilot.travel(10);
				pilot.stop();
			}
		} 
		// If obstacle LEFT of the line.
		else if (!Settings.obstacle) {
			Settings.obstacle = true;
			pilot.travel(-8);
			pilot.rotate(-50);
			while (!suppressed && light.getLightValue() < 50 && !(touch_l.isPressed() || touch_r.isPressed())) {
				pilot.steer(40, 60, true);
			}
			Settings.obstacle = false;
		}

		// Steers in arcs until line found.
		while (!suppressed && Settings.obstacle && light.getLightValue() < 50) {
			if (touch_l.isPressed() || touch_r.isPressed())
				pilot.rotate(50); // TODO wert genauer

			// too close
			else if (!pilot.isMoving() && sensor.getDistance() < distanceToWall) {
				pilot.steer(25, 10, true);
			}
			// too far
			else if (!pilot.isMoving()
					&& sensor.getDistance() >= 2 * distanceToWall) {
				pilot.rotate(-5);
			}
			// not so far
			else if (!pilot.isMoving()
					&& sensor.getDistance() >= distanceToWall) {
				pilot.steer(-40, -20, true);
			}
		}
		// If line found - leave obstacle mode.
		if (light.getLightValue() > 50 && Settings.obstacle) {
			pilot.rotate(80);
			Settings.obstacle = false;
			// pilot.travel(-5); TODO do we need it?
			//pilot.steer(20, 20, false);
			pilot.travel(5);
			Settings.motorAAngle = 0;
		}
		pilot.stop();
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}
