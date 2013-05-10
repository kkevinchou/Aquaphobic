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
	
	private Game game;
	private Player player;
	
	public PlayerController(GameContainer container, Game game, Player player) {
		this.game = game;
		this.player = player;
		
		provider = new InputProvider(container.getInput());
		provider.addListener(this);
		
		provider.bindCommand(new KeyControl(Input.KEY_LEFT), left);
		provider.bindCommand(new KeyControl(Input.KEY_RIGHT), right);
	}

	@Override
	public void controlPressed(Command command) {
		BasicCommand bCommand = (BasicCommand)command;
		
		if (bCommand.getName().equals("left")) {
			player.moveLeft();
		} else if (bCommand.getName().equals("right")) {
			player.moveRight();
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
