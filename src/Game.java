

import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import aqua.entity.BaseEntity;
import aqua.entity.EntityManager;
import aqua.entity.PhysEntity;
import aqua.entity.Platform;
import aqua.entity.Player;
import aqua.physics.BasicForce;
import aqua.physics.PhysicsEngine;

public class Game {
	private PhysicsEngine physicsEngine = PhysicsEngine.getInstance();
	
	private Player player1;
	private Player player2;
	
	private void initWalls() {
		int thickness = 25;
		
		PhysEntity bottomWall = new Platform(1, 600 - thickness - 1, 799, thickness);
		PhysEntity topWall = new Platform(1, 1, 799, thickness);
		PhysEntity leftWall = new Platform(1, thickness + 1, thickness, 600 - (2 * thickness) - 2);
		PhysEntity rightWall = new Platform(800 - thickness, thickness + 1, thickness, 600 - (2 * thickness) - 2);
		
		physicsEngine.queueAddition(bottomWall);
		physicsEngine.queueAddition(topWall);
		physicsEngine.queueAddition(leftWall);
		physicsEngine.queueAddition(rightWall);
	}
	
	private void initPlayer(GameContainer container) {
		player1 = new Player(350, 100, 50, 50);
		player1.addForce(new BasicForce(player1, 0, 1200));
		physicsEngine.queueAddition(player1);
		
		player2 = new Player(100, 100, 50, 50);
		player2.addForce(new BasicForce(player2, 0, 1200));
		physicsEngine.queueAddition(player2);
	}
	
	public void init(GameContainer container) throws SlickException {
		initPlayer(container);
		initWalls();
	}

	public void update(GameContainer container, int delta) throws SlickException {
		delta = Math.min(delta, 17);
		float deltaSeconds = (float)delta / 1000;
		
		Input input = container.getInput();
		
		if (input.isKeyDown(Input.KEY_D)) {
			player1.moveRight();
		} else {
			player1.stopMoveRight();
		}
		
		if (input.isKeyDown(Input.KEY_RIGHT)) {
			player2.moveRight();
		} else {
			player2.stopMoveRight();
		}
		
		if (input.isKeyDown(Input.KEY_A)) {
			player1.moveLeft();
		} else {
			player1.stopMoveLeft();
		}
		
		if (input.isKeyDown(Input.KEY_LEFT)) {
			player2.moveLeft();
		} else {
			player2.stopMoveLeft();
		}
		
		if (input.isKeyDown(Input.KEY_W)) {
			player1.jump();
		} else {
			player1.stopJump();
		}
		
		if (input.isKeyDown(Input.KEY_UP)) {
			player2.jump();
		} else {
			player2.stopJump();
		}
		
		if (input.isMouseButtonDown(0)) {
			player1.launchCord(input.getMouseX(), input.getMouseY());
		}
		
		if (input.isMouseButtonDown(1)) {
			player2.launchCord(input.getMouseX(), input.getMouseY());
		}
		
		physicsEngine.performTimeStep(deltaSeconds);
	}

	public void render(GameContainer container, Graphics graphics) throws SlickException {
		List<BaseEntity> entities = PhysicsEngine.getInstance().getEntities();
		
		for (BaseEntity entity : entities) {
			if (entity instanceof PhysEntity) {
				PhysEntity pEntity = (PhysEntity)entity;
				graphics.draw(pEntity.getCollisionShape());
			}
		}
	}
}
