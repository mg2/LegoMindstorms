package edu.kit.curiosity.behaviors.whip;

import edu.kit.curiosity.Settings;
import edu.kit.curiosity.behaviors.DriveForward;

public class WhipDriveForward extends DriveForward {
	@Override
	public boolean takeControl() {
		return Settings.afterWhip;
	}

}
