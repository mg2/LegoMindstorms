package edu.kit.curiosity.behaviors.slider;

import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import lejos.robotics.subsumption.CustomArbitrator;
import edu.kit.curiosity.LightCalibrate;
import edu.kit.curiosity.SensorHeadCalibrate;
import edu.kit.curiosity.Settings;
import edu.kit.curiosity.behaviors.SensorHeadPosition;

public class SliderMain {


	public static void main(String[] args) {
 new LightCalibrate();
		
		DifferentialPilot pilot = Settings.PILOT;
		Motor.A.setStallThreshold(10, 100);
		pilot.setTravelSpeed(pilot.getMaxTravelSpeed() * 0.5);

		new SensorHeadCalibrate();
		Settings.motorAAngle = 0;
		
		
		Behavior b1 = new SliderFollowWall(10);
		Behavior b3 = new SliderHitWall();
		Behavior s4 = new LineFound();
		Behavior b2 = new StartSlider();
		Behavior s5 = new SensorHeadPosition();
		
		Behavior[] sliderArr = {b1, b3, s4, b2, s5};
		CustomArbitrator sliderArbitrator = new CustomArbitrator(sliderArr);
		Thread t = new Thread(sliderArbitrator);
		t.start();
	}

}
