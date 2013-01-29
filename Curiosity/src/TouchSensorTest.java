import lejos.nxt.Button;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;

public class TouchSensorTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		TouchSensor t = new TouchSensor(SensorPort.S4);
		while (!Button.ESCAPE.isDown()) {
			System.out.println("Pressed: " + t.isPressed());
		}
	}
}
