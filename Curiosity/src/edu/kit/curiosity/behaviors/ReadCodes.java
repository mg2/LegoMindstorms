package edu.kit.curiosity.behaviors;

import edu.kit.curiosity.Settings;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class ReadCodes implements Behavior {
	public static boolean readState;
	private boolean suppressed = false;
	private boolean counted;
	
	private DifferentialPilot pilot;
	private LightSensor light;
	
	private int numOfTapes;
	
	public ReadCodes() {
		numOfTapes = 0;
		counted = false;
		readState = false;
		pilot = Settings.PILOT;
		light = Settings.LIGHT;
	}
	
	@Override
	public boolean takeControl() {
		return readState;
	}

	@Override
	public void action() {
		pilot.setTravelSpeed(Settings.DRIVE_SPEED / 2);
		suppressed = false;
		long timer = System.currentTimeMillis();
		numOfTapes = 0;
		counted = false;
		pilot.forward();
		while(!suppressed) {
			if(!suppressed && !counted 
					&& light.getLightValue() > 30) {
				numOfTapes++;
				System.out.println("TapeCount: " + numOfTapes);
				counted = true;
			} else if(!suppressed && counted
					&& light.getLightValue() < 30) {
				pilot.forward();	
				System.out.println("Black Screen.");
				counted = false;
			} 
			if(pilot.getMovementIncrement() > 10) {
				LCD.clear();
				// set States..
				System.out.println("CodeNumber: " + numOfTapes);
				// self-suppress (finished job)
				suppress();
			}
		}
		readState = false;
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}
