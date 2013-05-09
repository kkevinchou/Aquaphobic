import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;


public class BitsGame extends BasicGame {
	public static final String TITLE = "Aquaphobic";
	public static int WIDTH = 800;
	public static int HEIGHT = 600;
	
	public BitsGame(String title) {
		super(title);
	}

	public void init(GameContainer gc) throws SlickException {
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.setAntiAlias(true);
	}

	public void update(GameContainer gc, int delta) throws SlickException {
	}

	public static void main(String[] args) {
		BitsGame game = new BitsGame(BitsGame.TITLE);
	     try {
	          AppGameContainer app = new AppGameContainer(game);
	          app.setDisplayMode(WIDTH, HEIGHT, false);
	          app.setTargetFrameRate(60);
	          app.setShowFPS(false);
	          app.start();
	     } catch (SlickException e) {
	          e.printStackTrace();
	     }
	}
}
