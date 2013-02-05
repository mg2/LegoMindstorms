package edu.kit.curiosity.behaviors.bluetooth;

import lejos.nxt.Button;
import lejos.nxt.LCD;

public class Test {
	public static void main(String[] args) {
		/*
		 * GateControl initialisieren um das Tor "Gate" zu öffnen TurnControl
		 * ist für die Drehscheibe ("Turntable") ColorGateControl ist für das
		 * Tor vor dem Boss ("ColorGate")
		 */
		GateControl gateControl = new GateControl();
		TurnControl turnControl = new TurnControl();
		ColorGateControl cgc = new ColorGateControl();

//		System.out.println("Press button to open gate");
//		Button.waitForAnyPress();
//
//		while (!gateControl.connectionToGateSuccessful())
//			;
//		// Öffnet eine gewisse Zeit (10s)
//		gateControl.openGate();
//		gateControl.disconnectFromGate();
//
//		System.out.println("Gate opened");
//		Button.waitForAnyPress();
//		LCD.clear();
//		System.out.println("Press button to test turntable");
//		Button.waitForAnyPress();

		while (!turnControl.connectionToTurntableSuccessful())
			;
		turnControl.turnClockwise(90);

		// Wenn ihr fertig seid und raus fahrt
		turnControl.disconnectFromTurntable();

		System.out.println("Turntable turned");
		Button.waitForAnyPress();
		LCD.clear();
		System.out.println("Press button to test color gate");
		Button.waitForAnyPress();

		while (!cgc.connectionToColorGateSuccessful())
			;

		// die ID's der Farben... siehe Variables.java
		switch (cgc.readColor()) {
		case 0:
			System.out.println("Color = GREEN");
			break;
		case 1:
			System.out.println("Color = YELLOW");
			break;
		case 2:
			System.out.println("Color = RED");
			break;
		default:
			System.out.println("Error...");
		}
		// Wenn ihr fertig seid und durch seid
		cgc.disconnectFromGate();
		Button.waitForAnyPress();
		LCD.clear();
	}
}
