package edu.kit.curiosity.behaviors.slider;

import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import lejos.robotics.subsumption.CustomArbitrator;
import edu.kit.curiosity.Settings;
import edu.kit.curiosity.behaviors.DriveForward;
import edu.kit.curiosity.behaviors.MotorAStall;
import edu.kit.curiosity.behaviors.SensorHeadPosition;

public class SliderMain {


	public static void main(String[] args) {

		DifferentialPilot pilot = Settings.PILOT;
		pilot.setAcceleration((int) (pilot.getMaxTravelSpeed() * 10));
		pilot.setTravelSpeed(pilot.getMaxTravelSpeed());
		Settings.motorAAngle = 90;	
		
		Behavior s1 = new DriveForward();
		Behavior s2 = new SliderHit();
		Behavior s5 = new SensorHeadPosition();
		Behavior s6 = new MotorAStall();
		
		Behavior[] sliderArr = {s1, s2, s5, s6};
		CustomArbitrator sliderArbitrator = new CustomArbitrator(sliderArr);
		Thread t = new Thread(sliderArbitrator);
		t.start();
	}

}
