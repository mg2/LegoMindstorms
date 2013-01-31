import lejos.nxt.Button;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;


public class UltraSensorTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		UltrasonicSensor u = new UltrasonicSensor(SensorPort.S1);
		while(!Button.ESCAPE.isDown()) {
			if (Button.ENTER.isDown()) {
				//System.out.println("Range value: " + u.getRange());				
				System.out.println("Distance: " + u.getDistance());				
			}
		}
	}

}
