package edu.kit.curiosity.behaviors.slider;

import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import edu.kit.curiosity.Settings;

public class SliderHit implements Behavior {
	
	UltrasonicSensor sonic = Settings.SONIC;
	DifferentialPilot pilot = Settings.PILOT;
	TouchSensor touch_l = Settings.TOUCH_L;
	TouchSensor touch_r = Settings.TOUCH_R;

	@Override
	public boolean takeControl() {
		// TODO Auto-generated method stub
		return (touch_l.isPressed() || touch_r.isPressed()); // slider hit
	}

	@Override
	public void action() {
		pilot.stop();
		pilot.travel(-20); //go back 20cm
		while (sonic.getDistance() >= 30); // wait for slider to be there
		while (sonic.getDistance() <= 30); // wait for slider to be away
		pilot.travel(100); // drive 1m forward
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub

	}

}
