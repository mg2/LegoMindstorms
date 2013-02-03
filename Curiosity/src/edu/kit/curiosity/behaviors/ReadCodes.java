package edu.kit.curiosity.behaviors;

import edu.kit.curiosity.Settings;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;

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
		return readState && (light.getLightValue() > 30);
	}

	@Override
	public void action() {
		suppressed = false;

		counted = false;
		numOfTapes = 0;
		
//		pilot.travel(-5);
		pilot.forward();
		while(!suppressed) {
			if(!suppressed && !counted 
					&& light.getLightValue() > 45) {
				numOfTapes++;
				System.out.println("TapeCount: " + numOfTapes);
				counted = true;
			} else if(!suppressed && counted
					&& light.getLightValue() < 40) {
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
		
		pilot.stop();
		readState = false;
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}
