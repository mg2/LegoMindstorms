package edu.kit.curiosity.behaviors.slider;

import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import lejos.robotics.subsumption.CustomArbitrator;
import edu.kit.curiosity.LightCalibrate;
import edu.kit.curiosity.SensorHeadCalibrate;
import edu.kit.curiosity.Settings;
import edu.kit.curiosity.behaviors.ReadCodes;
import edu.kit.curiosity.behaviors.SensorHeadPosition;
import edu.kit.curiosity.behaviors.tapefollow.TapeFollow;

public class SliderMain {


	public static void main(String[] args) {

		new SensorHeadCalibrate();
		new LightCalibrate(false, false);
		
		DifferentialPilot pilot = Settings.PILOT;
		Motor.A.setStallThreshold(10, 100);
		pilot.setTravelSpeed(pilot.getMaxTravelSpeed() * 0.5);

		Settings.readState = false;
		Settings.motorAAngle = 0;
		
		Behavior t1 = new TapeFollow();
		Behavior t2 = new ReadCodes();
		Behavior b1 = new SliderFollowWall(10);
		Behavior b3 = new SliderHitWall();
		Behavior b2 = new StartSlider();
		Behavior s5 = new SensorHeadPosition();
		
		Behavior[] sliderArr = { t1, t2, b1, b3, b2 , s5};
		CustomArbitrator sliderArbitrator = new CustomArbitrator(sliderArr);
		Thread t = new Thread(sliderArbitrator);
		t.start();
	}

}
