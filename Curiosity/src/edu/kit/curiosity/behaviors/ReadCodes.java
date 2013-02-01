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
	
	private int numOfTapes;
	
	int settingsSilverLightNorm = 350;
	
	public ReadCodes() {
		numOfTapes = 0;
		READ_STATE = false;
		pilot = Settings.PILOT;
		light = Settings.LIGHT;
	}
	
	@Override
	public boolean takeControl() {
		return READ_STATE;
	}

	@Override
	public void action() {
		suppressed = false;
		pilot.forward();
		long timer = System.currentTimeMillis();
		numOfTapes = 0;
		while(!suppressed) {
			if((System.currentTimeMillis() - timer) > 3000) {
				System.out.println("CodeNumber: " + numOfTapes);
				System.out.println("");
				System.out.println("-> State Bridge?");
			}
			if(light.getNormalizedLightValue() > settingsSilverLightNorm) {
				numOfTapes++;
				timer = System.currentTimeMillis();
			}
		}
		READ_STATE = false;
	}

	@Override
	public void suppress() {
		suppressed = false;
	}

}
