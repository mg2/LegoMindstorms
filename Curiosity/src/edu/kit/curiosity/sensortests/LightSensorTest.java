package edu.kit.curiosity.sensortests;
import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;

/*
 * normalized light sensor values:
 * 	- tape: 600+ vs. black plane: 350-400?
 * 	- on bridge: ~500 vs. down bridge ~250
 */
public class LightSensorTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		LightSensor l = new LightSensor(SensorPort.S2);
		while(Button.ESCAPE.isUp()) {
			if (Button.ENTER.isDown()) {
				System.out.println("LichtNorm: " + Integer.toString(l.readNormalizedValue()));
				System.out.println("Licht: " + Integer.toString(l.readValue()));
			}
			if(Button.LEFT.isDown()) {
				l.calibrateHigh();
				System.out.println("calibrated high: " + l.getHigh());
			}
			if(Button.RIGHT.isDown()) {
				l.calibrateLow();
				System.out.println("calibrated low: " + l.getLow());
			}
		}
	}

}
