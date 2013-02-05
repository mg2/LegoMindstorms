package edu.kit.curiosity.behaviors.tapefollow;

import lejos.nxt.LightSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import lejos.robotics.subsumption.CustomArbitrator;
import edu.kit.curiosity.LightCalibrate;
import edu.kit.curiosity.SensorHeadCalibrate;
import edu.kit.curiosity.Settings;
import edu.kit.curiosity.behaviors.SensorHeadPosition;

public class TapeFollowMain {
	
	static DifferentialPilot pilot = Settings.PILOT;
	static LightSensor light = Settings.LIGHT;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/**
		 * !!!!!!MARTIN!!!!!!!!!
		 * !!!!!!WICHTIG!!!!!!!!
		 * 
		 * Geschwindigkeit setzen
		 */
		double speed = pilot.getMaxTravelSpeed() * Settings.tapeFollowSpeed;
		pilot.setTravelSpeed(speed);
		//pilot.setTravelSpeed(pilot.getMaxTravelSpeed() * 0.4);
		pilot.setRotateSpeed(pilot.getRotateMaxSpeed());
		new SensorHeadCalibrate();

		Settings.motorAAngle = 0;
		
		new LightCalibrate(false, true);
		
		Behavior t1 = new TapeFollow();
		Behavior t2 = new TapeGapFound();
		Behavior t3 = new TapeObstacleFound();
		Behavior t5 = new SensorHeadPosition();
		
		Behavior[] tapeFollowArray = {t1, t2, t3, t5};
		
		CustomArbitrator tapeFollowArbitrator = new CustomArbitrator(tapeFollowArray);
		Thread t = new Thread(tapeFollowArbitrator);
		t.start();
	}

}
