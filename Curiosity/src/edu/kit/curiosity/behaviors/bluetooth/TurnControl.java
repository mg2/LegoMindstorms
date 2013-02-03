package edu.kit.curiosity.behaviors.bluetooth;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.bluetooth.RemoteDevice;

import lejos.nxt.Sound;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;

/**
 * Use this class to communicate with the gates.
 * The gates stay open for a predefined time and automatically close afterwards.
 * The connection will be terminated automatically after some time, but please always disconnect 
 * manually as soon as possible so the gates aren't blocked longer than necessary (the NXTs can't handle
 * more than one incoming Bluetooth connection at once).
 * @author Sebastian
 * @version 11.12.2011, 17:00
 */
public class TurnControl {
	private RemoteDevice remoteDevice;
	private BTConnection connection;
	private DataInputStream inputStream;
	private DataOutputStream outputStream;

	/**
	 * Connects to the brick that controls the gates. Required before calling any other method.
	 * @param gateID the gate you want to control, see the GateCommon.GATE_* constants
	 * @return true if the connection to the gate was successful, false otherwise (brick not found, connection failed, ...)
	 */
	public boolean connectionToTurntableSuccessful() {
		String turntableBrickName;
		turntableBrickName = TurnCommon.TURNTABLE_BRICK_NAME;
		
		remoteDevice = Bluetooth.getKnownDevice(turntableBrickName);
		if (remoteDevice == null) {
			Sound.beep();
			return false;
		}
			
		
		

		connection = Bluetooth.connect(remoteDevice);
		if (connection == null) {
			Sound.beepSequenceUp();
			return false;
		}
		
		Sound.beep();

		inputStream = connection.openDataInputStream();
		outputStream = connection.openDataOutputStream();

		return (inputStream != null && outputStream != null);
	}
	
	
	/**
	 * Tells the brick to close the connection.
	 * @return true if the command to close the connection was sent, false otherwise
	 */
	public boolean disconnectFromTurntable() {
		boolean successful = sendCommand(TurnCommon.COMMAND_CLOSE_CONNECTION);
		if (successful)
			connection.close();
		return successful;
	}

	/**
	 * Tells the brick to turn the turntable.
	 * @return true if the command was sent, false otherwise
	 */
	public boolean turnClockwise(int angle) {
		return sendCommand(angle);
	}
	
	private boolean sendCommand(int command) {
		try {
			outputStream.writeInt(command);
			outputStream.flush();
			return true;
		} catch (IOException e) {
			return false;
		}
	}
}
