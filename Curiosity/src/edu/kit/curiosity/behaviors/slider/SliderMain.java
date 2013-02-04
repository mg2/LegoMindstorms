package edu.kit.curiosity.behaviors.slider;

import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import lejos.robotics.subsumption.CustomArbitrator;
import edu.kit.curiosity.Settings;
import edu.kit.curiosity.behaviors.DriveForward;
import edu.kit.curiosity.behaviors.SensorHeadPosition;

public class SliderMain {


	public static void main(String[] args) {

		DifferentialPilot pilot = Settings.PILOT;
		Motor.A.setStallThreshold(10, 100);
		pilot.setAcceleration((int) (pilot.getMaxTravelSpeed() * 10));
		pilot.setTravelSpeed(pilot.getMaxTravelSpeed() * 0.1);

		Settings.motorAAngle = 90;
		
		Behavior s1 = new DriveForward();
		Behavior s2 = new SliderHit();
		Behavior s5 = new SensorHeadPosition();
		
		Behavior[] sliderArr = {s1, s2, s5};
		CustomArbitrator sliderArbitrator = new CustomArbitrator(sliderArr);
		Thread t = new Thread(sliderArbitrator);
		t.start();
	}

}
