package tests;


import edu.kit.curiosity.Settings;
import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class TapeLostWithHead implements Behavior {
	//Auf dem schwarzen Planner entsprechen 90 Grad 136.5 CodeGrad
	private boolean suppressed = false;
	private static int light;
	private static int light2;
	
	DifferentialPilot dp;
	LightSensor ls;
	
	public TapeLostWithHead() {
		dp = Settings.PILOT;
		ls = Settings.LIGHT;
	}

	@Override
	public boolean takeControl() {
		if (ls.getLightValue() < 40) return true;
		else return false;
	}

	@Override
	public void action() {

		suppressed = false;
		light = ls.getLightValue();
		if (light < 0) light = 0;
		Motor.A.rotate(-10);
		light2 = ls.getLightValue();
		System.out.println(" [" + light + ", " + light2 + "]");
		if (light2 < 0) light2 = 0;

		if (light > 20 && light - light2 < 0 && light - light2 > -40) 
			{
				dp.rotate(-20);
				//System.out.print(" Right");
			}
		else if (light > 20 && light - light2 > 40) {
			dp.rotate(20);
			//System.out.print(" Left");
		}
		else {
			System.out.print(" N");
			Motor.A.rotateTo(0);
			Settings.angle = 15;
			while (!suppressed && ls.getLightValue() < 40) {
				System.out.print("2");
				if (!dp.isMoving()) {
					dp.rotate(Settings.angle, true);
					Settings.angle *= -2;
				}			
			}
		}
		Motor.A.rotateTo(0);
	}

	@Override
	public void suppress() {
		suppressed = true;
		Settings.angle = 15; //TODO ???
		dp.stop(); //TODO ??
	}

}
