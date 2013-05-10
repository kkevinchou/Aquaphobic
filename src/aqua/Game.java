package aqua;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import aqua.entity.BaseEntity;
import aqua.entity.EntityManager;
import aqua.entity.PhysEntity;
import aqua.entity.Platform;
import aqua.entity.Player;
import aqua.physics.Force;
import aqua.physics.PhysicsEngine;

public class Game {
	private PhysicsEngine physicsEngine = PhysicsEngine.getInstance();
	private EntityManager entityManager = EntityManager.getInstance();
	
	private Player player;
	private PlayerController playerController;
	
	private void initWalls() {
		int thickness = 25;
		// bottom
		entityManager.add(new Platform(1, 600 - thickness - 1, 799, thickness));
		// top
		entityManager.add(new Platform(1, 1, 799, thickness));
		// left
		entityManager.add(new Platform(1, thickness + 1, thickness, 600 - (2 * thickness) - 2));
		// left
		entityManager.add(new Platform(800 - thickness - 1, thickness + 1, thickness, 600 - (2 * thickness) - 2));
	}
	
	private void initPlayer(GameContainer container) {
		player = new Player(350, 100, 50, 50);
		player.addForce(new Force(0, 800));
		playerController = new PlayerController(container, this, player);
		entityManager.add(player);
		
	}
	
	public void init(GameContainer container) throws SlickException {
		initPlayer(container);
		initWalls();
	}

	public void update(GameContainer container, int delta) throws SlickException {
		float deltaSeconds = (float)delta / 1000;
		physicsEngine.performTimeStep(deltaSeconds);
	}

	public void render(GameContainer container, Graphics graphics) throws SlickException {
		for (BaseEntity entity : entityManager.getEntities()) {
			Rectangle r1 = new Rectangle(entity.getX(), entity.getY(), entity.getWidth(), entity.getHeight());
			
			PhysEntity p = (PhysEntity)entity;
			Rectangle hitBox = p.getHitBox();
			
			graphics.draw(r1);
			graphics.draw(hitBox);
		}
	}
}
