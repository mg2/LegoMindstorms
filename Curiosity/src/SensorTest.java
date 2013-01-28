import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;


public class SensorTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		UltrasonicSensor u = new UltrasonicSensor(SensorPort.S1);
		LightSensor l = new LightSensor(SensorPort.S2);
		while(Button.ESCAPE.isUp()) {
			if (Button.ENTER.isDown()) {
				System.out.println("LichtNorm: " + Integer.toString(l.readNormalizedValue()));
				System.out.println("Licht: " + Integer.toString(l.readValue()));
				System.out.println("Ultra: " + u.getDistance());				
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
