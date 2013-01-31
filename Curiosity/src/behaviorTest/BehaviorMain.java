package behaviorTest;

import lejos.nxt.Button;
import lejos.nxt.SensorPort;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class BehaviorMain {
	static int i = 0;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		while (Button.ESCAPE.isUp()) {
			Behavior b1 = new Drive();
			Behavior b2 = new Hit(SensorPort.S1);
			Behavior[] bArray = { b1, b2 };
			Arbitrator arby = new Arbitrator(bArray);
			arby.start();
		}
	}

}
