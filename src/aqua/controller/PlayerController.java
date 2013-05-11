package aqua.controller;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.command.BasicCommand;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.command.InputProviderListener;
import org.newdawn.slick.command.KeyControl;

import aqua.Game;
import aqua.entity.Player;
import aqua.physics.BasicForce;

public class PlayerController implements InputProviderListener {
	private InputProvider provider;
	
	private Command left = new BasicCommand("left");
	private Command right = new BasicCommand("right");
	private Command jump = new BasicCommand("jump");
	private Command exit = new BasicCommand("exit");
	
	private Game game;
	private Player player;
	private GameContainer container;
	
	private void initCommands(Player player) {
		right = new MovementCommand("right", new BasicForce(player, 800, 0));
	}
	
	public PlayerController(GameContainer container, Game game, Player player) {
		this.container = container;
		this.game = game;
		this.player = player;
		
		initCommands(player);
		
		provider = new InputProvider(container.getInput());
		provider.addListener(this);
		
//		provider.bindCommand(new KeyControl(Input.KEY_LEFT), left);
		provider.bindCommand(new KeyControl(Input.KEY_A), left);
		
//		provider.bindCommand(new KeyControl(Input.KEY_RIGHT), right);
		provider.bindCommand(new KeyControl(Input.KEY_D), right);
		
//		provider.bindCommand(new KeyControl(Input.KEY_UP), jump);
		provider.bindCommand(new KeyControl(Input.KEY_W), jump);
		
		provider.bindCommand(new KeyControl(Input.KEY_ESCAPE), exit);
	}

	@Override
	public void controlPressed(Command command) {
		BasicCommand bCommand = command instanceof BasicCommand ? (BasicCommand)command : null;
		MovementCommand mCommand = command instanceof MovementCommand ? (MovementCommand)command : null;
		
		if (bCommand.getName().equals("left")) {
			player.moveLeft();
		} else if (bCommand.getName().equals("right")) {
			player.moveRight();
		} else if (bCommand.getName().equals("jump")) {
			player.jump();
		} else if (bCommand.getName().equals("exit")) {
			container.exit();
		}
	}

	@Override
	public void controlReleased(Command command) {
		BasicCommand bCommand = command instanceof BasicCommand ? (BasicCommand)command : null;
		MovementCommand mCommand = command instanceof MovementCommand ? (MovementCommand)command : null;
		
		if (bCommand.getName().equals("left")) {
			player.stopMoveLeft();
		} else if (bCommand.getName().equals("right")) {
			player.stopMoveRight();
		} else if (bCommand.getName().equals("jump")) {
			player.stopJump();
		}
	}

}
