import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;
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
		
		final int blackWhiteThreshold = 50;
		int i = 0;
		int l = 0;
		int r = 0;
		int out = 15;
		int tr = 90;
		boolean loop = true;

		betterLightCalibrate();
		// new LightCalibrate(true, true);

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
			if (i > out && i <= 3 * out) {
				// last out turns were in same direction
				if (r > out || l > out) {
					tr = 150;
				}
			} else if (i > 3 * out) { // more than 2 * out
				System.out.println("*2");
				if (r > 2 * out || l > 2 * out) {
					int m = 1;
					if (r > 2 * out)
						m = -1;
					while (i >= out / 2
							&& light.getLightValue() < blackWhiteThreshold) {
						pilot.steer(m * tr, m * (-1) * 10, false); // travel back
						i--;
						Thread.sleep(100);
					}
					System.out.println("GAP");
					Button.ENTER.waitForPressAndRelease();
				}
				// values to default
				i = 0;
				l = 0;
				r = 0;
				tr = 90;
			}
			i++;
			Thread.sleep(100);
		}
		Motor.B.flt();
		Motor.C.flt();
		LCD.clear();
		System.out.print("Program stopped");
		Button.ENTER.waitForPressAndRelease();

	}

	private static void betterLightCalibrate() {
		System.out.println("Press to calibrate.");
		Button.ENTER.waitForPressAndRelease();
		int lights[] = new int[300];

		for (int j = 0; j < lights.length; j++) {
			pilot.travel(1, true);
			lights[j] = light.getNormalizedLightValue();
		}
		java.util.Arrays.sort(lights);
		int min = 1024;
		int max = -1;
		int tempSum = 0;
		for (int j = 0; j < 100; j++) {
			tempSum += lights[j];
		}
		min = tempSum / 100;
		tempSum = 0;
		for (int j = lights.length - 100; j < lights.length; j++) {
			tempSum += lights[j];
		}
		max = tempSum / 100;
		System.out.println("min: " + min + ", max: " + max);
		light.setLow(min);
		light.setHigh(max);
		System.out.println("Press ENTER to continue.");
		Button.ENTER.waitForPressAndRelease();
	}

}
