package aqua.controller;

import org.newdawn.slick.command.BasicCommand;

import aqua.physics.Force;

public class MovementCommand extends BasicCommand {
	public Force force;

	public MovementCommand(String name, Force force) {
		super(name);
		this.force = force;
	}

}
