package aqua.entity;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import aqua.effect.Effect;
import aqua.physics.Force;
import aqua.physics.Vector2D;

// The basic object to be manipulated by the physics engine
public abstract class PhysEntity extends BaseEntity {
	public enum CollisionType { RECTANGLE, CIRCLE, LINE }
	
	private float xSpeed;
	private float ySpeed;
	private float mass = 1;
	
	protected Shape collisionShape;
	
	private CollisionType collisionType;
	
	private List<Force> forces;
	
	List<Effect> effects;
	public PhysEntity attachment;

	public PhysEntity(float x, float y, float width, float height, CollisionType collisionType) {
		super(x, y, width, height);
		if (collisionType == CollisionType.RECTANGLE) {
			collisionShape = new Rectangle(x, y, width, height);
		} else if (collisionType == CollisionType.CIRCLE) {
			collisionShape = new Circle(x, y, width);
		}
		
		xSpeed = 0;
		ySpeed = 0;
		
		forces = new ArrayList<Force>();
		effects = new ArrayList<Effect>();
		this.collisionType = collisionType;
	}
	
	public void applyEffect(Effect effect) {
		effect.onApply();
		effects.add(effect);
	}
	
	public void removeEffect(Effect effect) {
		effect.onExpire();
		effects.remove(effect);
	}
	
	public void prePhysics() {
		for (Effect effect : effects) {
			effect.prePhysics();
		}
	}
	
	public CollisionType getCollisionType() {
		return collisionType;
	}
	
	public void setPosition(float x, float y) {
		super.setPosition(x, y);
		
		if (collisionShape instanceof Circle) {
			Circle c = (Circle)collisionShape;
			collisionShape.setLocation(x - c.getRadius(), y - c.getRadius());
		} else {
			collisionShape.setLocation(x, y);
		}
	}
	
	public void setXSpeed(float xs) {
		this.xSpeed = xs;
	}
	
	public void setYSpeed(float ys) {
		this.ySpeed = ys;
	}

	public void setSpeed(float xs, float ys) {
		this.xSpeed = xs;
		this.ySpeed = ys;
	}
	
	public void incrementSpeed(float dx, float dy) {
		xSpeed += dx;
		ySpeed += dy;
	}

	public boolean collidiesWith(PhysEntity target) {
		return (getCollisionShape().intersects(target.getCollisionShape()));
	}

	public Shape getCollisionShape() {
		return collisionShape;
	}
	
	boolean debug = true;

	public void performTimeStep(float elapsedTime) {
		Vector2D netForce = getNetForce();
		
		float accelX = netForce.getX() / mass;
		float accelY = netForce.getY() / mass;
		incrementSpeed(accelX * elapsedTime, accelY * elapsedTime);
		
		float dx = xSpeed * elapsedTime;
		float dy = ySpeed * elapsedTime;
		setPosition(x + dx, y + dy);
	}
	
	public void addForce(Force force) {
		forces.add(force);
	}
	
	public void removeForce(Force force) {
		forces.remove(force);
	}
	
	private Vector2D getNetForce() {
		Vector2D result = new Vector2D();
		
		for (Force force : forces) {
			result = result.add(force.getVector());
		}
		
		return result;
	}
	
	public boolean skipCollisionResolution() {
		return false;
	}
	
	private void onCollision(PhysEntity target) {
		return;
	}
	
	public void triggerOnCollision(PhysEntity target) {
		onCollision(target);
		target.onCollision(this);
	}
	
	public void destroy() {
		EntityManager.getInstance().remove(getId());
	}
//	
//	public void applyCollisionEffect(PhysEntity target) {
//		return;
//	}
//	
//	public void mutuallyApplyCollisionEffects(PhysEntity target) {
//		applyCollisionEffect(target);
//		target.applyCollisionEffect(this);
//	}
}
