package edu.kit.curiosity.behaviors.slider;

import edu.kit.curiosity.Settings;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class SliderHit implements Behavior {
	
	UltrasonicSensor sonic = Settings.SONIC;
	DifferentialPilot pilot = Settings.PILOT;
	TouchSensor touch_l = Settings.TOUCH_L;
	TouchSensor touch_r = Settings.TOUCH_R;

	@Override
	public boolean takeControl() {
		// TODO Auto-generated method stub
		return (touch_l.isPressed() || touch_r.isPressed());
	}

	@Override
	public void action() {
		pilot.stop();
		pilot.travel(-20);
		while (sonic.getDistance() >= 30);
		while (sonic.getDistance() <= 30);
		pilot.travel(100);
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub

	}

}
