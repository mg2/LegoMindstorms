import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;

public class MitkoTest {
	private static DifferentialPilot diffPilot;
	static int lightLow = 350;
	static int lightHigh = 650;
	static int tresh = 40;
	
	static LightSensor light = new LightSensor(SensorPort.S2);
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		diffPilot = new DifferentialPilot(8.5, 14, Motor.C, Motor.B);
		double travelSpeed = diffPilot.getTravelSpeed();
		double rotateSpeed = diffPilot.getRotateSpeed();
		configDiffPilot(rotateSpeed / 7, travelSpeed / 7);
		
		Button.ESCAPE.addButtonListener(new ButtonListener() {
		      public void buttonPressed(Button b) {
			        System.exit(0);
		      }

		      public void buttonReleased(Button b) {
		      }
		    });
		Button.LEFT.addButtonListener(new ButtonListener() {
		      public void buttonPressed(Button b) {
		      }

		      public void buttonReleased(Button b) {
		  		stopAndGetPosition(Motor.A);
		      }
		    });
		
		//calibrateLightSensor();
		light.setLow(lightLow);
		light.setHigh(lightHigh);
		
		System.out.println("ENTER for IR test.\nLEFT checks if Motor.A is stalled/moving and fixes it.");
		Button.ENTER.waitForPressAndRelease();
		while (light.getLightValue() < tresh) {
			System.out.println(light.getLightValue());
			diffPilot.forward();
		}
		
		while (true) {
			while (light.getLightValue() > tresh) {
				System.out.println(light.getLightValue());
				diffPilot.forward();
			}
			double angle = -10;
			while (light.getLightValue() < tresh) {
				System.out.println(light.getLightValue());
				diffPilot.rotate(angle);
				angle = angle * (-2);
			}
		}

	}
	
	/**
	 * Checks if the motor is moving or is stalled and stops it.
	 * @param m the motor
	 */
	private static void stopAndGetPosition(NXTRegulatedMotor m) {
		if (m.isMoving() || m.isStalled()) m.stop();
		System.out.println(m.getPosition());		
	}
	
	private static void configDiffPilot(double d, double e) {
		diffPilot.setRotateSpeed(d);
		diffPilot.setTravelSpeed(e);
	}

	public static void calibrateLightSensor() {
		Button.ENTER.waitForPressAndRelease();
		System.out.println("Calibrate LightSensor!");
		System.out.print("Calibrate Low: ");
		Button.ENTER.waitForPressAndRelease();
		light.calibrateLow();
		lightLow = light.getLow();
		System.out.println(lightLow);
		Button.ENTER.waitForPressAndRelease();
		System.out.print("Calibrate High: ");
		light.calibrateHigh();
		lightHigh = light.getHigh();
		System.out.println(lightHigh);
		System.out.println("");
		System.out.println("Start Running with ENTER!");
		Button.ENTER.waitForPressAndRelease();
		LCD.clear();
	}

}
