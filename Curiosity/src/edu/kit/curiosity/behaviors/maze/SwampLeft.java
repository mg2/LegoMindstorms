package edu.kit.curiosity.behaviors.maze;

import edu.kit.curiosity.Settings;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class SwampLeft implements Behavior {

	private LightSensor light;
	private DifferentialPilot pilot;
	
	private boolean suppressed;
	
	public SwampLeft() {
		light = Settings.LIGHT;
		pilot = Settings.PILOT;
	}
	
	@Override
	public boolean takeControl() {

		return (Settings.IN_SWAMP && light.getNormalizedLightValue() < Settings.Black_Light);
	}

	@Override
	public void action() {
		suppressed = false;
		Settings.IN_SWAMP = false;
		Motor.A.flt(true);
		Motor.A.rotateTo(0);
		Motor.A.flt(true);
		while (Motor.A.isMoving() && !suppressed) {
			Thread.yield();
		}
		Motor.A.stop();

	}

	@Override
	public void suppress() {
		suppressed = true;

	}

}
