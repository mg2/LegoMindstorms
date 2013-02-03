package edu.kit.curiosity.behaviors.tape;

import edu.kit.curiosity.Settings;
import lejos.nxt.LightSensor;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class ObstacleFound implements Behavior {

	private boolean suppressed = false;
	TouchSensor touch_l = Settings.TOUCH_L;
	TouchSensor touch_r = Settings.TOUCH_R;
	DifferentialPilot pilot = Settings.PILOT;
	UltrasonicSensor sensor = Settings.SONIC;
	LightSensor light = Settings.LIGHT;

	private final int distanceToWall = 10;
	//private int angle = 50;

	@Override
	public boolean takeControl() {
		return (Settings.obstacle || touch_l.isPressed() || touch_r.isPressed());
	}

	@Override
	public void action() {
		suppressed = false;
		if (!Settings.obstacle) { // If not in obstacle mode - initialize
									// obstacle mode
			Settings.obstacle = true;
			Settings.motorAAngle = 0;
			pilot.travel(-8);
			pilot.rotate(100);
			pilot.travel(10);
		}
		while (!suppressed && light.getLightValue() < 50) { // arcs until line
															// found
			if (touch_l.isPressed() || touch_r.isPressed())
				pilot.rotate(50); // TODO wert genauer
						
			//too close
			else if (!pilot.isMoving()
					&& sensor.getDistance() < distanceToWall) {
				//pilot.arc(0, 20, true);
				pilot.steer(25, 10, true);
			} 
			
			//not so far
			else if (!pilot.isMoving()
					&& sensor.getDistance() >= distanceToWall) {
				//pilot.arc(0, -20, true);
				pilot.steer(-40, -20, true);
			}
		}
		if (light.getLightValue() > 50) { // if line found - leave obstacle mode
			pilot.rotate(120);
			Settings.obstacle = false;
			//pilot.travel(-5); TODO do we need it?
			Settings.motorAAngle = 90;
		}
		pilot.stop();
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}
