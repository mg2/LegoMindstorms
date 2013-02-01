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
		return (light.getNormalizedLightValue() > Settings.swampLight) && !Settings.afterSwamp;
	}

	@Override
	public void action() {
		suppressed = false;
		System.out.println("IN SWAMP");
		Settings.inSwamp = true;
		Settings.motorAAngle = 90;
		Motor.A.flt(true);
		Motor.A.rotateTo(Settings.motorAAngle);
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
