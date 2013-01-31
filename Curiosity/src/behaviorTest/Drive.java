package behaviorTest;

import lejos.nxt.Motor;
import lejos.robotics.subsumption.Behavior;

public class Drive implements Behavior {
	private boolean suppressed = false;

	public boolean takeControl() {
		return true;
	}

	public void suppress() {
		suppressed = true;
	}

	public void action() {
		suppressed = false;
		Motor.A.forward();
		Motor.C.forward();
		BehaviorMain.i++;
		while (!suppressed) {
			System.out.println("drive " + BehaviorMain.i);
			Thread.yield();
		}
		Motor.A.stop(); // clean up
		Motor.C.stop();
	}
}