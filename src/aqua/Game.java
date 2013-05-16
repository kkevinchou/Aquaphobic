package aqua;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import aqua.controller.PlayerController;
import aqua.controller.PlayerController2;
import aqua.entity.BaseEntity;
import aqua.entity.EntityManager;
import aqua.entity.PhysEntity;
import aqua.entity.Platform;
import aqua.entity.Player;
import aqua.physics.BasicForce;
import aqua.physics.PhysicsEngine;

public class Game {
	private PhysicsEngine physicsEngine = PhysicsEngine.getInstance();
	private EntityManager entityManager = EntityManager.getInstance();
	
	private Player player1;
	private Player player2;
	
	private PlayerController playerController1;
	private PlayerController2 playerController2;
	
	private void initWalls() {
		int thickness = 25;
		// bottom
		entityManager.add(new Platform(1, 600 - thickness - 1, 799, thickness));
		// top
		entityManager.add(new Platform(1, 1, 799, thickness));
		// left
		entityManager.add(new Platform(1, thickness + 1, thickness, 600 - (2 * thickness) - 2));
		// right
		entityManager.add(new Platform(800 - thickness, thickness + 1, thickness, 600 - (2 * thickness) - 2));
	}
	
	private void initPlayer(GameContainer container) {
		player1 = new Player(350, 100, 50, 50);
		player1.addForce(new BasicForce(player1, 0, 1200));
		playerController1 = new PlayerController(container, this, player1);
		entityManager.add(player1);
		
		player2 = new Player(100, 100, 50, 50);
		player2.addForce(new BasicForce(player2, 0, 1200));
		playerController2 = new PlayerController2(container, this, player2);
		entityManager.add(player2);
	}
	
	public void init(GameContainer container) throws SlickException {
		initPlayer(container);
		initWalls();
	}

	public void update(GameContainer container, int delta) throws SlickException {
		delta = Math.min(delta, 17);
		float deltaSeconds = (float)delta / 1000;
		
//		Input input = container.getInput();
		
//		if (input.isKeyDown(Input.KEY_D)) {
//			player.moveRight();
//		} else {
//			player.stopMoveRight();
//		}
//		
//		if (input.isKeyDown(Input.KEY_A)) {
//			player.moveLeft();
//		} else {
//			player.stopMoveLeft();
//		}
//		
//		if (input.isKeyDown(Input.KEY_W)) {
//			player.jump();
//		}
		
		physicsEngine.performTimeStep(deltaSeconds);
	}

	public void render(GameContainer container, Graphics graphics) throws SlickException {
		for (BaseEntity entity : entityManager.getEntities()) {
			if (entity instanceof PhysEntity) {
				PhysEntity pEntity = (PhysEntity)entity;
				graphics.draw(pEntity.getCollisionShape());
			}
		}
	}
}
