import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;
import edu.kit.curiosity.LightCalibrate;
import edu.kit.curiosity.Settings;

public class FULineFollow {

	/**
	 * @param args
	 */
	public static int t = 0;
	public static int lv = 0;
	public static double m= -1;
	public static int temp = 0;
	
	public static void main(String[] args) {

		DifferentialPilot dp = Settings.PILOT;
		dp.setTravelSpeed(dp.getMaxTravelSpeed() * 0.1);
		dp.setRotateSpeed(dp.getRotateMaxSpeed() * 0.1);
		
//		Button.LEFT.waitForPressAndRelease();
////		System.out.println(dp.getMinRadius());
//		dp.steer(-50, -20, true);
//		Button.ENTER.waitForPressAndRelease();
//		
		new LightCalibrate(true, true);
		LightSensor ls = Settings.LIGHT;

		while (true) {
			lv = ls.getLightValue();
			if (lv < 0) lv = 0;
			else if (lv > 100) lv = 100;
			
			if (t < 20) {
				t++;
			}
			else if (t == 20) {
				temp = lv;
				t++;
			}
			else if (t < 50) {
				t++;		
			}
			else if (t == 50) {
				if (temp - lv > 20) m *= -1;
				t = 0;
			}
			//System.out.println(lv - temp + " " + (int) m);
			//Delay.msDelay(100);
			dp.steer(m * (100 - lv), m * 1, true);
		}

	}

}
