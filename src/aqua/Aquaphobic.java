package aqua;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.gui.TextField;


public class Aquaphobic extends BasicGame {
	public static final String TITLE = "Aquaphobic";
	public static int WIDTH = 800;
	public static int HEIGHT = 600;
	public static int FPS = 60;
	
	private final Color backgroundColor = Color.black;
	
	UnicodeFont font;
	TextField textField;
	Game game = new Game();
	
	public Aquaphobic(String title) {
		super(title);
	}
	
	private void initFonts() {
    	try {
            font = new UnicodeFont("res/BRLNSR.TTF", 20, false, false);
            font.addAsciiGlyphs();
            font.addGlyphs(400,600);	// Setting the unicode Range
            font.getEffects().add(new ColorEffect(java.awt.Color.white));
			font.loadGlyphs();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	private void initTextField(GameContainer container) {
		textField = new TextField(container, font, 200, 200, 200, 200);
	}

	public void init(GameContainer container) throws SlickException {
		container.getGraphics().setAntiAlias(true);
		container.getGraphics().setBackground(backgroundColor);
		
		initFonts();
		initTextField(container);
		game.init(container);
	}

	public void update(GameContainer container, int delta) throws SlickException {
		game.update(container, delta);
	}

	public void render(GameContainer container, Graphics graphics) throws SlickException {
//		font.drawString(100, 100, "Who's aquaphobic? YOU ARE!");
//		textField.render(container, graphics);
		game.render(container, graphics);
	}

	public static void main(String[] args) {
		Aquaphobic game = new Aquaphobic(TITLE);
	     try {
	          AppGameContainer app = new AppGameContainer(game);
	          app.setDisplayMode(WIDTH, HEIGHT, false);
	          app.setTargetFrameRate(FPS);
	          app.setShowFPS(false);
	          app.start();
	     } catch (SlickException e) {
	          e.printStackTrace();
	     }
	}
}
