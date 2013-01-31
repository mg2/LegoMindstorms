package behaviorTest;

import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.subsumption.Behavior;

public class Hit implements Behavior {
	private TouchSensor touch;
	private UltrasonicSensor sonar;
	private boolean suppressed = false;
	
	public Hit(SensorPort port )
	{
		sonar = new UltrasonicSensor( port );
	}
	
	public boolean takeControl() {
		return touch.isPressed() || sonar.getDistance() < 25;
	}
	
	public void suppress() {
		suppressed = true;
	}
	
	public void action() {
		suppressed = false;
		Motor.A.rotate(-180, true);
		Motor.C.rotate(-360, true);
		Main.i++;
		while( Motor.C.isMoving() && !suppressed ) {
	    	System.out.println("hit " + Main.i);
			Thread.yield();
		}
		
		Motor.A.stop();
		Motor.C.stop();
	}

}
