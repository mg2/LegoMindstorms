package edu.kit.curiosity.behaviors.tapefollow;

import lejos.nxt.LightSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import lejos.robotics.subsumption.CustomArbitrator;
import edu.kit.curiosity.LightCalibrate;
import edu.kit.curiosity.Settings;
import edu.kit.curiosity.behaviors.MotorAStall;
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
		double speed = pilot.getMaxTravelSpeed() * 0.15; //Linie-Suche = 15%, Haengebruecke = ca 40%
		pilot.setTravelSpeed(speed);
		pilot.setRotateSpeed(pilot.getRotateMaxSpeed());		

		Settings.motorAAngle = 97; // TODO auf 90 wieder 
		
		new LightCalibrate(true, true);
		
		Behavior t1 = new TapeFollow();
		Behavior t2 = new TapeGapFound();
		Behavior t3 = new TapeObstacleFound();
		Behavior t5 = new SensorHeadPosition();
		Behavior t6 = new MotorAStall();
		
		Behavior[] tapeFollowArray = {t1, t2, t3, t5, t6};
		
		CustomArbitrator tapeFollowArbitrator = new CustomArbitrator(tapeFollowArray);
		Thread t = new Thread(tapeFollowArbitrator);
		t.start();
	}

}
