package aqua;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.command.BasicCommand;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.command.InputProviderListener;
import org.newdawn.slick.command.KeyControl;

import aqua.entity.Player;

public class PlayerController implements InputProviderListener {
	private InputProvider provider;
	
	private Command left = new BasicCommand("left");
	private Command right = new BasicCommand("right");
	private Command jump = new BasicCommand("jump");
	private Command exit = new BasicCommand("exit");
	
	private Game game;
	private Player player;
	private GameContainer container;
	
	public PlayerController(GameContainer container, Game game, Player player) {
		this.container = container;
		this.game = game;
		this.player = player;
		
		provider = new InputProvider(container.getInput());
		provider.addListener(this);
		
		provider.bindCommand(new KeyControl(Input.KEY_LEFT), left);
		provider.bindCommand(new KeyControl(Input.KEY_RIGHT), right);
		provider.bindCommand(new KeyControl(Input.KEY_UP), jump);
		provider.bindCommand(new KeyControl(Input.KEY_ESCAPE), exit);
		provider.bindCommand(new KeyControl(Input.KEY_Q), exit);
	}

	@Override
	public void controlPressed(Command command) {
		BasicCommand bCommand = (BasicCommand)command;
		
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
		BasicCommand bCommand = (BasicCommand)command;
		
		if (bCommand.getName().equals("left")) {
			player.moveRight();
		} else if (bCommand.getName().equals("right")) {
			player.moveLeft();
		}
	}

}
