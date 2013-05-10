package aqua;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import aqua.entity.BaseEntity;
import aqua.entity.EntityManager;
import aqua.entity.Player;
import aqua.physics.Force;
import aqua.physics.PhysicsEngine;

public class Game {
	private PhysicsEngine physicsEngine = PhysicsEngine.getInstance();
	private EntityManager entityManager = EntityManager.getInstance();
	
	private Player player;
	private PlayerController playerController;
	
	public void init(GameContainer container) throws SlickException {
		player = new Player(100, 100, 100, 100);
		player.addForce(new Force(0, 400));
		playerController = new PlayerController(container, this, player);
		entityManager.add(player);
	}

	public void update(GameContainer container, int delta) throws SlickException {
		float deltaSeconds = (float)delta / 1000;
		physicsEngine.performTimeStep(deltaSeconds);
	}

	public void render(GameContainer container, Graphics graphics) throws SlickException {
		for (BaseEntity entity : entityManager.getEntities()) {
			Rectangle r = new Rectangle(entity.getX(), entity.getY(), entity.getWidth(), entity.getHeight());
			graphics.draw(r);
		}
	}
}
