package aqua.entity;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.geom.Rectangle;

import aqua.physics.Force;
import aqua.physics.Point;
import aqua.physics.Vector2D;

// The basic object to be manipulated by the physics engine
public abstract class PhysEntity extends BaseEntity {
	protected Rectangle hitBox;
	protected float xSpeed;
	protected float ySpeed;
	
	public boolean onGround;
	
	private List<Force> forces;

	public PhysEntity(float x, float y, float width, float height) {
		super(x, y, width, height);
		onGround = false;
		hitBox = new Rectangle(x, y, width, height);
		forces = new ArrayList<Force>();
	}
	
	public void setPosition(float x, float y) {
		super.setPosition(x, y);
		hitBox.setLocation(x, y);
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
		return (hitBox.intersects(target.hitBox));
	}

	public Rectangle getHitBox() {
		return hitBox;
	}

	public Point getTopLeft() {
		return new Point(hitBox.getX(), hitBox.getY());
	}

	public Point getBottomRight() {
		return new Point(hitBox.getX() + hitBox.getWidth() - 1, hitBox.getY() + hitBox.getHeight() - 1);
	}

	public void performTimeStep(float elapsedTime) {
		Vector2D netForce = getNetForce();
		
		float accelX = netForce.getX();
		float accelY = netForce.getY();
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
	
//	public boolean isPlayer() {
//		return false;
//	}
//	
//	public boolean isItem() {
//		return false;
//	}
//	
	public boolean skipCollisionResolution() {
		return false;
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
