package aqua.physics;

import java.util.HashSet;
import java.util.List;

import aqua.entity.BaseEntity;
import aqua.entity.EntityManager;
import aqua.entity.PhysEntity;

public class PhysicsEngine {
	private static PhysicsEngine instance;
	private EntityManager entityManager;
	HashSet<String> handledCollisionEffects;

	private PhysicsEngine() {
		entityManager = EntityManager.getInstance();
		handledCollisionEffects = new HashSet<String>();
	}

	public static PhysicsEngine getInstance() {
		if (instance == null) {
			instance = new PhysicsEngine();
		}
		return instance;
	}
	
	// Perform a physics time step based on the elapsedTime
	// Handles collision detection and resolution
	public void performTimeStep(float elapsedTime) {
		List<BaseEntity> entities = entityManager.getEntities();
		
		handledCollisionEffects.clear();
		
		for (BaseEntity currentBaseEntity : entities) {
			PhysEntity current = (PhysEntity)currentBaseEntity;
			
			float oldX = current.getX();
			float oldY = current.getY();
			current.performTimeStep(elapsedTime);
			current.onGround = false;
			
			if (current.skipCollisionResolution()) {
				continue;
			}
			
			for (BaseEntity targetBaseEntity : entities) {
				PhysEntity target = (PhysEntity)targetBaseEntity;
				
				// Don't check collisions with itself
				if (current.getId() == target.getId()) {
					continue;
				}
				
				if (current.collidiesWith(target)) {
					if (!handledCollisionEffects.contains(current.getId() + " " + target.getId())) {
						// Indicate that this collision effect has been handled
						// so that we don't handle it again during the same timestep
						handledCollisionEffects.add(current.getId() + " " + target.getId());
						handledCollisionEffects.add(target.getId() + " " + current.getId());
					}
					resolveCollision(oldX, oldY, current, target);
				}
			}
		}
	}

	// Resolve collisions between two entities
	private void resolveCollision(float oldX, float oldY, PhysEntity current, PhysEntity target) {
		float newX = current.getX();
		float newY = current.getY();
		
		// Rewind the position of the entity to before the collision occured
		current.setPosition(oldX, oldY);
		
		Point cTopLeft = current.getTopLeft();
		Point cBottomRight = current.getBottomRight();
		
		Point tTopLeft = target.getTopLeft();
		Point tBottomRight = target.getBottomRight();
		
//		Rectangle cHitBox = current.getHitBox();
//		Rectangle tHitBox = target.getHitBox();
//		System.out.println(cHitBox.getX() + " " + cHitBox.getY() + " " + cHitBox.getWidth() + " " + cHitBox.getHeight());
//		System.out.println(tHitBox.getX() + " " + tHitBox.getY() + " " + tHitBox.getWidth() + " " + tHitBox.getHeight());

		if (cTopLeft.x >= tBottomRight.x) {
			// Collision from the right
			// T <- C
			current.setPosition(tBottomRight.x + 1, newY);
		} else if (cBottomRight.y <= tTopLeft.y) {
			// Collision from above
			// C
			// v
			// T
			current.setPosition(newX, tTopLeft.y - current.getHeight());
			current.setYSpeed(0);
			current.onGround = true;
		}  else if (cTopLeft.y >= tBottomRight.y) {
			// Collision from below
			// T
			// ^
			// C
			current.setPosition(newX, tBottomRight.y + 1);
			current.setYSpeed(0);
		} else if (cBottomRight.x <= tTopLeft.x) {
			// Collision from the left
			// C -> T
			current.setPosition(tTopLeft.x - current.getWidth() - 1, newY);
		}
	}
}
