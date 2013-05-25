package aqua.entity;

import java.util.HashSet;
import java.util.Set;

import aqua.effect.PullEffect;
import aqua.physics.PhysicsEngine;
import aqua.physics.Vector2D;

public class CordHead extends PhysEntity {
	private static final float radius = 5;
	private static final float baseSpeed = 800;
	
	private Player owner;
	private PhysEntity target;
	
	private float targetOffsetX;
	private float targetOffsetY;
	
	private PullEffect pullEffect;
	
	private CordTail tail;
	
	private boolean collided;
	
	private float secondsSinceCollision;
	
	private Set<PhysEntity> ignoreCollisionSet;

	public CordHead(float x, float y, Vector2D direction, Player owner) {
		super(x, y, radius, radius, CollisionType.CIRCLE);
		direction = direction.mult(baseSpeed);
		setSpeed(direction.getX(), direction.getY());
		this.owner = owner;
		
		targetOffsetX = 0;
		targetOffsetY = 0;
		
		collided = false;
		secondsSinceCollision = 0;
		
		tail = new CordTail(this, owner);
		PhysicsEngine.getInstance().queueAddition(tail);

		ignoreCollisionSet = new HashSet<PhysEntity>();
		ignoreCollisionSet.add(owner);
		ignoreCollisionSet.add(tail);
	}
	
	public CordTail getTail() {
		return tail;
	}
	
	public Player getOwner() {
		return owner;
	}
	
	public PhysEntity getTarget() {
		return target;
	}
	
	@Override
	public void performTimeStep(float elapsedTimeSeconds) {
		if (collided && target instanceof Platform) {
			secondsSinceCollision += elapsedTimeSeconds;
			if (secondsSinceCollision >= 2.0) {
				owner.destroyCord();
			}
		}
		
		if (target instanceof Player) {
			this.setPosition(target.getX() + targetOffsetX, target.getY() + targetOffsetY);
		} else {
			super.performTimeStep(elapsedTimeSeconds);
		}
	}
	
	@Override
	protected void onCollision(PhysEntity target) {
		if (collided || ignoreCollisionSet.contains(target)
				|| !(target instanceof Player || target instanceof Platform
				|| target instanceof CordHead || target instanceof CordTail )) {
			return;
		}
		
		if (owner.attachment != null) {
			CordHead attackerCord = (CordHead)owner.attachment;
			if (target.equals(attackerCord) || target.equals(attackerCord.getTail()) || target.equals(attackerCord.owner)) {
				return;
			}
		}
		
		if (target instanceof Player) {
			if (target.attachment != null) {
				((CordHead)target.attachment).owner.destroyCord();
				owner.destroyCord();
				return;
			}
			
			targetOffsetX = this.getX() - target.getX();
			targetOffsetY = this.getY() - target.getY();
			
			pullEffect = new PullEffect(owner, target);
			((Player)target).applyEffect(pullEffect);
			target.attachment = this;
			
			owner.onCordHitSuccessful((Player)target);
		}
		
		if (target instanceof CordTail) {
			((CordTail)target).owner.destroyCord();
			owner.destroyCord();
		}
		
		if (target instanceof CordHead) {
			((CordHead)target).owner.destroyCord();
			owner.destroyCord();
		}
		
		collided = true;
		setSpeed(0, 0);
		this.target = target;
	}
	
	@Override
	public void destroy() {
		super.destroy();
		if (target instanceof Player) {
			target.removeEffect(pullEffect);
			target.attachment = null;
		}
		if (tail != null) {
			tail.destroy();
		}
	}
}
