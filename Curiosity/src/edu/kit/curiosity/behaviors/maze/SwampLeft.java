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

		return (Settings.inSwamp && (light.getNormalizedLightValue() < Settings.blackLight));
	}

	@Override
	public void action() {
		suppressed = false;
		System.out.println("OUT SWAMP");
		Settings.inSwamp = false;
		Settings.afterSwamp = true;
		Settings.motorAAngle = 0;
		Motor.A.flt(true);
		Motor.A.rotateTo(Settings.motorAAngle);
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
