package edu.kit.curiosity.behaviors;

import edu.kit.curiosity.Settings;
import lejos.nxt.LightSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class ReadCodes implements Behavior {
	public static boolean READ_STATE;
	private boolean suppressed = false;
	
	private DifferentialPilot pilot;
	private LightSensor light;
	
	private static int numOfTapes = 0;
	
	int settingsSilverLightNorm = 500;
	
	public ReadCodes() {
		READ_STATE = false;
		pilot = Settings.PILOT;
		light = Settings.LIGHT;
	}
	
	@Override
	public boolean takeControl() {
		// if its in a state with the need to read a code...??
		return READ_STATE;
	}

	@Override
	public void action() {
		pilot.setTravelSpeed(Settings.DRIVE_SPEED);
		pilot.forward();
		int timer = 0;
		while(!suppressed) {
			if(timer > 1000) {
				System.out.println("CodeNumber: " + numOfTapes);
				System.out.println("");
				System.out.println("-> State Bridge?");
			}
			if(light.getNormalizedLightValue() > settingsSilverLightNorm) {
				numOfTapes++;
				timer = 0;
			}
			timer++;
		}
	}

	@Override
	public void suppress() {
		suppressed = false;
	}

}
