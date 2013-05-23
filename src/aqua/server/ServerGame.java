package aqua.server;

import java.util.List;

import aqua.entity.BaseEntity;
import aqua.entity.EntityManager;
import aqua.entity.PhysEntity;
import aqua.entity.Platform;
import aqua.physics.PhysicsEngine;

public class ServerGame {
	private PhysicsEngine physicsEngine = PhysicsEngine.getInstance();
	
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
	
	public ServerGame() {
		initWalls();
	}

	public void update(int delta) {
		delta = Math.min(delta, 17);
		float deltaSeconds = (float)delta / 1000;
		
		physicsEngine.performTimeStep(deltaSeconds);
	}
	
	public List<BaseEntity> getEntities() {
		return physicsEngine.getEntities();
	}
}
