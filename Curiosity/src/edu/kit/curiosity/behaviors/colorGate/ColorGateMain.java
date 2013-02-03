package edu.kit.curiosity.behaviors.colorGate;

import tests.GapFound;
import tests.LineFollow;
import tests.ObstacleFound;
import tests.TapeLost;
import tests.TapeMain;
import lejos.nxt.Motor;
import lejos.robotics.subsumption.Behavior;
import lejos.robotics.subsumption.CustomArbitrator;
import edu.kit.curiosity.LightCalibrate;
import edu.kit.curiosity.Settings;
import edu.kit.curiosity.behaviors.MotorAStall;
import edu.kit.curiosity.behaviors.SensorHeadPosition;

public class ColorGateMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		new ColorGateMain();
		
		Motor.A.setSpeed(Motor.A.getMaxSpeed() / 5);

		Settings.PILOT.setTravelSpeed(Settings.PILOT.getMaxTravelSpeed() * 0.75);
		Settings.PILOT.setRotateSpeed(Settings.PILOT.getMaxRotateSpeed() * 0.15);
		Settings.motorAAngle = 90;
		
		new LightCalibrate(true, false, false, true, true);
		
		// TODO Something with bluetooth communication
		// TODO Linie finden
		Behavior tf1 = new LineFollow();
		Behavior tf2 = new TapeLost();
		Behavior tf3 = new FirstColor();
		Behavior tf4 = new FollowWallColor(12);
		Behavior tf5 = new ColorFound();
		Behavior tf6 = new HitWallColor();
		Behavior tf7 = new SensorHeadPosition();
		Behavior tf8 = new MotorAStall();
		
		Behavior[] tapeFollow = {tf1, tf2, tf3, tf4, tf5, tf6, tf7, tf8};
		CustomArbitrator tapeFollowArbitrator = new CustomArbitrator(tapeFollow);	
		Thread t = new Thread(tapeFollowArbitrator);
		t.start();

	}

}
