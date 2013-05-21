package aqua.physics;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.newdawn.slick.geom.Rectangle;

import aqua.entity.BaseEntity;
import aqua.entity.EntityManager;
import aqua.entity.PhysEntity;
import aqua.entity.PhysEntity.CollisionType;
import aqua.entity.Player;

public class PhysicsEngine {
	private static PhysicsEngine instance;
	private EntityManager entityManager;
	
	private List<BaseEntity> creationQueue;
	private List<BaseEntity> removalQueue;

	private PhysicsEngine() {
		entityManager = EntityManager.getInstance();
		creationQueue = new ArrayList<BaseEntity>();
		removalQueue = new ArrayList<BaseEntity>();
	}

	public static PhysicsEngine getInstance() {
		if (instance == null) {
			instance = new PhysicsEngine();
		}
		return instance;
	}
	
	public void queueRemoval(BaseEntity entity) {
		removalQueue.add(entity);
	}
	
	public void queueAddition(BaseEntity entity) {
		creationQueue.add(entity);
	}
	
	private void addEntitiesFromQueue() {
		for (BaseEntity entity : creationQueue) {
			entityManager.add(entity);
		}
		creationQueue.clear();
	}
	
	private void removeEntitiesFromQueue() {
		for (BaseEntity entity : removalQueue) {
//			entityManager.remove(entity.getId());
			entityManager.remove(entity);
		}
		removalQueue.clear();
	}
	
	// Perform a physics time step based on the elapsedTime
	// Handles collision detection and resolution
	public void performTimeStep(float elapsedTime) {
		List<BaseEntity> entities = entityManager.getEntities();
		
		HashSet<String> handledCollisionEffects = new HashSet<String>();
		
		for (BaseEntity currentBaseEntity : entities) {
			PhysEntity current = (PhysEntity)currentBaseEntity;
			
			current.prePhysics();
			
			float oldX = current.getX();
			float oldY = current.getY();
			current.performTimeStep(elapsedTime);
			
			if (current instanceof Player) {
				((Player)current).setIsOnGround(false);
			}
			
			if (current.skipCollisionResolution()) {
				continue;
			}

			for (BaseEntity targetBaseEntity : entities) {
				PhysEntity target = (PhysEntity)targetBaseEntity;
				
				// Don't check collisions with itself
				if (current.equals(target)) {
					continue;
				}
				
				if (current.collidiesWith(target)) {
					if (!handledCollisionEffects.contains(current.getId() + " " + target.getId())) {
						// Indicate that this collision effect has been handled
						// so that we don't handle it again during the same timestep
						handledCollisionEffects.add(current.getId() + " " + target.getId());
						handledCollisionEffects.add(target.getId() + " " + current.getId());
						
						current.triggerOnCollision(target);
					}
					
					if (current.getCollisionType() == CollisionType.RECTANGLE && 
							target.getCollisionType() == CollisionType.RECTANGLE) {
						resolveCollision(oldX, oldY, current, target);
					}
				}
			}
		}
		
		addEntitiesFromQueue();
		removeEntitiesFromQueue();
	}

	// Resolve collisions between two entities
	private void resolveCollision(float oldX, float oldY, PhysEntity current, PhysEntity target) {
		float newX = current.getX();
		float newY = current.getY();
		
		// Rewind the position of the entity to before the collision occured
		current.setPosition(oldX, oldY);
		
		Rectangle cHitBox = (Rectangle)current.getCollisionShape();
		Rectangle tHitBox = (Rectangle)target.getCollisionShape();
		
		Point cTopLeft = new Point(cHitBox.getX(), cHitBox.getY());
		Point cBottomRight = new Point(cHitBox.getX() + cHitBox.getWidth() - 1, cHitBox.getY() + cHitBox.getHeight() - 1);
		
		Point tTopLeft = new Point(tHitBox.getX(), tHitBox.getY());
		Point tBottomRight = new Point(tHitBox.getX() + tHitBox.getWidth() - 1, tHitBox.getY() + tHitBox.getHeight() - 1);
		
		if (cTopLeft.x >= tBottomRight.x) {
			// Collision from the right
			// T <- C
			current.setPosition(tBottomRight.x + 2, newY);
		} else if (cBottomRight.y <= tTopLeft.y) {
			// Collision from above
			// C
			// v
			// T
			current.setPosition(newX, tTopLeft.y - current.getHeight());
			current.setYSpeed(0);
			if (current instanceof Player) {
				Player p = (Player)current;
				p.setIsOnGround(true);
			}
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
			current.setPosition(tTopLeft.x - cHitBox.getWidth() - 1, newY);
		}
	}
}
