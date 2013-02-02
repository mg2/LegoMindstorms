package edu.kit.curiosity.behaviors.tape;

import edu.kit.curiosity.Settings;
import lejos.nxt.LightSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class TapeLost implements Behavior {
	//Auf dem schwarzen Planner entsprechen 90 Grad 136.5 CodeGrad
	private boolean suppressed = false;
	
	DifferentialPilot pilot;
	LightSensor light;
	
	public TapeLost() {
		pilot = Settings.PILOT;
		light = Settings.LIGHT;
	}

	@Override
	public boolean takeControl() {
		if (light.getLightValue() < 20) return true;
		else return false;
	}

	@Override
	public void action() {
		//System.out.print(" Lost");
		suppressed = false;
		boolean flag = false;
		if (Settings.fromLeft) {
			Settings.angle = -20;
		} else {
			Settings.angle = 20;
		}
		
		while (!suppressed && light.getLightValue() < 60) { //until suppressed or line found
			//for GAPS:
			if (!pilot.isMoving() && Math.abs(Settings.angle) >= 600)	{
				pilot.rotate(Math.signum(Settings.angle) * 135, true); //should be looking forward
				Settings.angle = 999;
			}
			else if (!pilot.isMoving()) { 
				pilot.rotate(Settings.angle, true);
				Settings.angle *= -2;
				
				//for GAPS:
				if (!flag && Math.abs(Settings.angle) >= 480) { //for gaps searching
					Settings.angle = (int) (Math.signum(Settings.angle) * 300); //sonst ueberdreht
					flag = true;
				}
			}
		}
		pilot.stop();
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}
