import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import edu.kit.curiosity.LightCalibrate;
import edu.kit.curiosity.Settings;

public class FULineFollow {
	static DifferentialPilot pilot = Settings.PILOT;
	static LightSensor light = Settings.LIGHT;

	/**
	 * @param args
	 */
	public static void main(String[] args) throws InterruptedException {

		pilot.setTravelSpeed(pilot.getMaxTravelSpeed() * 0.15);
		pilot.setRotateSpeed(pilot.getRotateMaxSpeed());
		
		final int blackWhiteThreshold = 20;
		int i = 0;
		int l = 0;
		int r = 0;
		int out = 150;
		int tr = 50;
		boolean loop = true;

		new LightCalibrate(true, true);
		//new LightCalibrate(true, false, false, true, false);

		while (loop) {
			if (light.getLightValue() > blackWhiteThreshold) {
				// On white, turn right
				l = 0;
				// System.out.print("right ");
				pilot.steer(-tr, -10, true);
				r++;
			} else {
				// On black, turn left
				r = 0;
				// System.out.print("left ");
				pilot.steer(tr, 10, true);
				r = 1;
				l++;
			}
			// between out and 2 * out
			if (i > out && i <= 2 * out) {
				// last out turns were in same direction
				if (r > out || l > out) {
					tr = 150;
				}
			} else if (i > 2 * out) { // more than 2 * out
				if (r > 2 * out || l > 2 * out) {
					int m = 1;
					if (r > 2 * out)
						m = -1;
					pilot.setTravelSpeed(pilot.getMaxTravelSpeed());
					while (i >= out
							&& light.getLightValue() < blackWhiteThreshold) {
						pilot.steer(m * tr, m * (-1) * 10, true); // travel back
						i--;
						Thread.sleep(100);
					}
					pilot.setTravelSpeed(pilot.getMaxTravelSpeed() * 0.2);
					System.out.println("GAP");
					//Button.ENTER.waitForPressAndRelease();
				}
				// values to default
				i = 0;
				l = 0;
				r = 0;
				tr = 90;
			}
			i++;
			Thread.sleep(10);
		}
		Motor.B.flt();
		Motor.C.flt();
		LCD.clear();
		System.out.print("Program stopped");
		Button.ENTER.waitForPressAndRelease();

	}
}
