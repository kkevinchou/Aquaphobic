package aqua.client;

import java.util.List;

import knetwork.managers.ClientNetworkManager;
import knetwork.message.messages.Message;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.gui.TextField;

import aqua.message.AquaMessageFactory;
import aqua.message.PlayerActionMessage;
import aqua.message.ServerUpdateMessage;

public class ClientGame extends BasicGame {
	private static final String TITLE = "Aquaphobic";
	private static int WIDTH = 800;
	private static int HEIGHT = 600;
	private static int FPS = 60;
	private static final boolean singlePlayer = false;
	private SinglePlayerServerThread serverThread;
	
	private ClientNetworkManager clientNetworkManager;
//	private String serverIp = "192.168.226.128";
	private String serverIp = "127.0.0.1";
	private final int serverPort = 8087;
	
	
	private final Color backgroundColor = Color.black;
	private UnicodeFont font;
	private TextField textField;
	
	private List<Shape> drawShapes;

	private ClientGame(String title) {
		super(title);
		clientNetworkManager = new ClientNetworkManager();
		clientNetworkManager.setMessageFactory(new AquaMessageFactory());
	}

	private void initFonts() {
		try {
			font = new UnicodeFont("res/BRLNSR.TTF", 20, false, false);
			font.addAsciiGlyphs();
			font.addGlyphs(400, 600); // Setting the unicode Range
			font.getEffects().add(new ColorEffect(java.awt.Color.white));
			font.loadGlyphs();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	private void initTextField(GameContainer container) {
		textField = new TextField(container, font, 200, 200, 200, 200);
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		container.getGraphics().setAntiAlias(true);
		container.getGraphics().setBackground(backgroundColor);

		initFonts();
		initTextField(container);
		
		if (singlePlayer) {
			serverIp = "127.0.0.1";
			serverThread = new SinglePlayerServerThread();
			serverThread.start();
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		
		if (clientNetworkManager.register(serverIp, serverPort)) {
			System.out.println("SUCCESSFULLY REGISTERED!");
		}
	}

	@Override
	public void render(GameContainer container, Graphics graphics) throws SlickException {
		graphics.setAntiAlias(true);
		
		if (drawShapes == null) {
			return;
		}
		
		for (Shape shape : drawShapes) {
			graphics.draw(shape);
		}
	}

	@Override
	public void update(GameContainer container, int graphics) throws SlickException {
		Message message = null;
		
		int counter = 0;
		while (true) {
			Message temp = clientNetworkManager.recv();
			if (temp == null) {
				break;
			} else {
				counter++;
				message = temp;
			}
		}
//		System.out.println("READ " + counter + " messages");
		
		if (message instanceof ServerUpdateMessage) {
			drawShapes = ((ServerUpdateMessage)message).getShapes();
		}
		
		Input input = container.getInput();
		PlayerActionMessage actionMessage = constructPlayerActionMessage(input);
		clientNetworkManager.send(actionMessage);
		
	}
	
	private PlayerActionMessage constructPlayerActionMessage(Input input) {
		boolean isLeft = input.isKeyDown(Input.KEY_A);
		boolean isRight = input.isKeyDown(Input.KEY_D);
		boolean isJump = input.isKeyDown(Input.KEY_W);
		boolean isLaunch = input.isMouseButtonDown(0);
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();
		
		return new PlayerActionMessage(isLeft, isRight, isJump, isLaunch, mouseX, mouseY);
	}

	public static void main(String[] args) {
		ClientGame game = new ClientGame(TITLE);
		try {
			AppGameContainer app = new AppGameContainer(game);
			app.setDisplayMode(WIDTH, HEIGHT, false);
			app.setTargetFrameRate(FPS);
			app.setShowFPS(true);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
