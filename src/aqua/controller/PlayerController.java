package aqua.controller;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.command.BasicCommand;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.command.InputProviderListener;
import org.newdawn.slick.command.KeyControl;
import org.newdawn.slick.command.MouseButtonControl;

import aqua.Game;
import aqua.entity.Player;
import aqua.physics.BasicForce;

public class PlayerController implements InputProviderListener {
	private InputProvider provider;
	
	private Command left = new BasicCommand("left");
	private Command right = new BasicCommand("right");
	private Command jump = new BasicCommand("jump");
	private Command exit = new BasicCommand("exit");
	private Command cord = new BasicCommand("cord");
	
	private Game game;
	private Player player;
	private GameContainer container;
	
	Input input;
	
	public PlayerController(GameContainer container, Game game, Player player) {
		this.container = container;
		this.game = game;
		this.player = player;
		input = container.getInput();
		
		provider = new InputProvider(container.getInput());
		provider.addListener(this);
		
//		provider.bindCommand(new KeyControl(Input.KEY_LEFT), left);
		provider.bindCommand(new KeyControl(Input.KEY_A), left);
		
//		provider.bindCommand(new KeyControl(Input.KEY_RIGHT), right);
		provider.bindCommand(new KeyControl(Input.KEY_D), right);
		
		provider.bindCommand(new KeyControl(Input.KEY_SPACE), jump);
		provider.bindCommand(new KeyControl(Input.KEY_W), jump);
		
		provider.bindCommand(new KeyControl(Input.KEY_ESCAPE), exit);
		
		provider.bindCommand(new MouseButtonControl(0), cord);
	}

	@Override
	public void controlPressed(Command command) {
		System.out.println("COMMAND! " +  command);
		BasicCommand bCommand = command instanceof BasicCommand ? (BasicCommand)command : null;
		
		if (bCommand.getName().equals("left")) {
			player.moveLeft();
		} else if (bCommand.getName().equals("right")) {
			player.moveRight();
		} else if (bCommand.getName().equals("jump")) {
			player.jump();
		} else if (bCommand.getName().equals("exit")) {
			container.exit();
		} else if (bCommand.getName().equals("cord")) {
			int mouseX = input.getMouseX();
			int mouseY = input.getMouseY();
			player.launchCord(mouseX, mouseY);
		}
	}

	@Override
	public void controlReleased(Command command) {
		BasicCommand bCommand = command instanceof BasicCommand ? (BasicCommand)command : null;
		
		if (bCommand.getName().equals("left")) {
			player.stopMoveLeft();
		} else if (bCommand.getName().equals("right")) {
			player.stopMoveRight();
		} else if (bCommand.getName().equals("jump")) {
			player.stopJump();
		}
	}

}
