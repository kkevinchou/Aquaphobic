package aqua.client;

import java.util.List;

import knetwork.client.ClientNetworkManager;
import knetwork.message.Message;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.gui.TextField;

import aqua.message.ServerUpdateMessage;
import aqua.message.UpdateEntity;

public class ClientGame extends BasicGame {
	private ClientNetworkManager clientNetworkManager;
//	private final String serverIp = "192.168.226.128";
	private final String serverIp = "127.0.0.1";
	private final int serverPort = 8087;

	private final Color backgroundColor = Color.black;

	UnicodeFont font;
	TextField textField;
	
	private List<UpdateEntity> updateEntities;

	public ClientGame(String title) {
		super(title);
		clientNetworkManager = new ClientNetworkManager();
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
		if (clientNetworkManager.register(serverIp, serverPort)) {
			System.out.println("SUCCESSFULLY REGISTERED!");
		}
	}

	@Override
	public void render(GameContainer container, Graphics graphics) throws SlickException {
		graphics.setAntiAlias(true);
		if (updateEntities == null) {
			return;
		}
		
		for (UpdateEntity entity : updateEntities) {
			graphics.draw(entity.shape);
		}
		
	}

	@Override
	public void update(GameContainer container, int graphics) throws SlickException {
		Message m = clientNetworkManager.recv();
		if (m instanceof ServerUpdateMessage) {
			updateEntities = ((ServerUpdateMessage)m).entities;
		}
	}

}
