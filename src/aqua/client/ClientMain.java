package aqua.client;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class ClientMain {
	public static final String TITLE = "Aquaphobic";
	public static int WIDTH = 800;
	public static int HEIGHT = 600;
	public static int FPS = 60;
	
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