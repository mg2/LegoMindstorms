package edu.kit.curiosity.behaviors.maze;

import edu.kit.curiosity.Settings;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class SwampDetected implements Behavior {
	
	private LightSensor light;
	private DifferentialPilot pilot;
	
	private boolean suppressed;
	
	public SwampDetected() {
		light = Settings.LIGHT;
		pilot = Settings.PILOT;
	}

	@Override
	public boolean takeControl() {
		return light.getNormalizedLightValue() > Settings.Swamp_Light;
	}

	@Override
	public void action() {
		suppressed = false;
		Settings.IN_SWAMP = true;
		Motor.A.flt(true);
		Motor.A.rotateTo(90);
		Motor.A.flt(true);
		pilot.forward();
		while (pilot.isMoving() && !suppressed) {
			Thread.yield();
		}
		pilot.stop();
		Motor.A.stop();
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}
