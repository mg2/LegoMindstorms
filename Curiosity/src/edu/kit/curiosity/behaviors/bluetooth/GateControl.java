package edu.kit.curiosity.behaviors.bluetooth;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.bluetooth.RemoteDevice;

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
public class GateControl {
	private RemoteDevice remoteDevice;
	private BTConnection connection;
	private DataInputStream inputStream;
	private DataOutputStream outputStream;
	private int connectedGate = 0;

	/**
	 * Connects to the brick that controls the gates. Required before calling any other method.
	 * @param gateID the gate you want to control, see the GateCommon.GATE_* constants
	 * @return true if the connection to the gate was successful, false otherwise (brick not found, connection failed, ...)
	 */
	public boolean connectionToGateSuccessful(int gateID) {
		connectedGate = gateID;
		
		String gateBrickName;
		if (gateID == GateCommon.GATE_1)
			gateBrickName = GateCommon.GATE1_BRICK_NAME;
		else
			gateBrickName = GateCommon.GATE23_BRICK_NAME;
		
		remoteDevice = Bluetooth.getKnownDevice(gateBrickName);
		if (remoteDevice == null)
			return false;

		connection = Bluetooth.connect(remoteDevice);
		if (connection == null)
			return false;

		inputStream = connection.openDataInputStream();
		outputStream = connection.openDataOutputStream();

		return (inputStream != null && outputStream != null);
	}
	
	
	/**
	 * Tells the brick to close the connection.
	 * @return true if the command to close the connection was sent, false otherwise
	 */
	public boolean disconnectFromGate() {
		boolean successful = sendCommand(GateCommon.COMMAND_CLOSE_CONNECTION);
		if (successful)
			connection.close();
		return successful;
	}

	/**
	 * Tells the brick to open the gate.
	 * @return true if the command to open the gate was sent, false otherwise
	 */
	public boolean openGate() {
		return sendCommand(connectedGate);
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
