package edu.kit.curiosity.behaviors;

import lejos.nxt.Motor;
import lejos.robotics.subsumption.Behavior;

public class PilotStall implements Behavior {
	// TODO
	// TODO
	// TODO
	// TODO
	// TODO

	@Override
	public boolean takeControl() {
		return (Motor.B.isStalled() || Motor.C.isStalled());
	}

	@Override
	public void action() {
		System.out.println("Pilot stall");
		Motor.B.flt();
		Motor.C.flt();
		//otor.B.
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub

	}

}
