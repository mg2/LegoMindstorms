package lejos.robotics.navigation;

import lejos.robotics.RegulatedMotor;

public class CustomDifferentialPilot extends DifferentialPilot {

	public CustomDifferentialPilot(final double wheelDiameter, final double trackWidth, final RegulatedMotor leftMotor,
			final RegulatedMotor rightMotor) {
		super(wheelDiameter, trackWidth, leftMotor, rightMotor);

	}

	public CustomDifferentialPilot(final double wheelDiameter, final double trackWidth, final RegulatedMotor leftMotor,
			final RegulatedMotor rightMotor, final boolean reverse) {
		super(wheelDiameter, trackWidth, leftMotor, rightMotor, reverse);
	}

	public CustomDifferentialPilot(double leftWheelDiameter, double rightWheelDiameter, double trackWidth,
			RegulatedMotor leftMotor, RegulatedMotor rightMotor, boolean reverse) {
		super(leftWheelDiameter, rightWheelDiameter, trackWidth, leftMotor, rightMotor, reverse);
	}

	public void forward() {
		super.backward();
	}

	public void backward() {
		super.forward();
	}

}
