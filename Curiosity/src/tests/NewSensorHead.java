package tests;

import edu.kit.curiosity.Settings;
import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.util.Delay;

public class NewSensorHead {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Motor.A.setStallThreshold(10, 100);
		Motor.A.setSpeed((int) (Motor.A.getMaxSpeed() * 0.1));

		Motor.A.rotate(180, true);
		while (!Motor.A.isStalled());
		Motor.A.rotate(-20);
		Delay.msDelay(1000);
		Motor.A.resetTachoCount();
	}

}
