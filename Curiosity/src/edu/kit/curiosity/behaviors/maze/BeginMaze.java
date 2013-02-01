package edu.kit.curiosity.behaviors.maze;

import edu.kit.curiosity.Settings;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

/**
 * The class {@code BeginMaze} describes the Behavior which takes place, if the
 * robot is at the beginning of the Maze.
 * 
 * @author Team Curiosity
 * 
 */
public class BeginMaze implements Behavior {

	private boolean suppressed;

	private DifferentialPilot pilot;

	/**
	 * Constructs a new BeginMaze Behavior
	 */
	public BeginMaze() {
		pilot = Settings.PILOT;
	}

	/**
	 * The Behavior takes control, if the robot is at the beginning of the maze.
	 */
	@Override
	public boolean takeControl() {

		return Settings.atStartOfMaze;
	}

	/**
	 * The robot travels in an arc to the right until he finds a wall.
	 */
	@Override
	public void action() {
		suppressed = false;
		pilot.arcForward(-30);
		while (pilot.isMoving() && !suppressed) {
			Thread.yield();
		}
	}

	/**
	 * Initiates the cleanup when this Behavior is suppressed
	 */
	@Override
	public void suppress() {
		suppressed = true;

	}

}
