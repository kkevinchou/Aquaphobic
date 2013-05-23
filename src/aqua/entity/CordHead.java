package aqua.entity;

import java.util.Timer;
import java.util.TimerTask;

import aqua.effect.PullEffect;
import aqua.physics.PhysicsEngine;
import aqua.physics.Vector2D;

public class CordHead extends PhysEntity {
	private static final float radius = 5;
	private static final float baseSpeed = 800;
	
	private Player owner;
	private Player target;
	
	private float targetOffsetX;
	private float targetOffsetY;
	
	private PullEffect pullEffect;
	
	private CordTail tail;
	
	private boolean collided;
	
	Timer timer;

	public CordHead(float x, float y, Vector2D direction, Player owner) {
		super(x, y, radius, radius, CollisionType.CIRCLE);
		direction = direction.mult(baseSpeed);
		setSpeed(direction.getX(), direction.getY());
		this.owner = owner;
		
		targetOffsetX = 0;
		targetOffsetY = 0;
		
		collided = false;
		
		tail = new CordTail(this, owner);
		PhysicsEngine.getInstance().queueAddition(tail);
		timer = new Timer();
	}
	
	public Player getOwner() {
		return owner;
	}
	
	public Player getTarget() {
		return target;
	}
	
	@Override
	public void performTimeStep(float elapsedTime) {
		if (target == null) {
			super.performTimeStep(elapsedTime);
		} else {
			this.setPosition(target.getX() + targetOffsetX, target.getY() + targetOffsetY);
		}
	}
	
	@Override
	protected void onCollision(PhysEntity target) {
		if (collided || owner.equals(target) || (tail != null && tail.equals(target))
				|| !(target instanceof Player || target instanceof Platform
				|| target instanceof CordHead || target instanceof CordTail )) {
			return;
		}
		
		collided = true;
		setSpeed(0, 0);
		
		if (target instanceof Platform) {
			timer.schedule(new TimerTask() {
				  public void run() {
					  owner.destroyCord();
				  }
			}, 2000);
		}
		
		if (target instanceof Player) {
			this.target = (Player)target;
			
			targetOffsetX = this.getX() - target.getX();
			targetOffsetY = this.getY() - target.getY();
			
			pullEffect = new PullEffect(owner, target);
			((Player)target).applyEffect(pullEffect);
			target.attachment = this;
			
			owner.onCordHitSuccessful(this.target);
		}
		
		if (target instanceof CordTail) {
			((CordTail)target).owner.destroyCord();
			owner.destroyCord();
		}
		
		if (target instanceof CordHead) {
			((CordHead)target).owner.destroyCord();
			owner.destroyCord();
		}
	}
	
	@Override
	public void destroy() {
		super.destroy();
		timer.cancel();
		if (tail != null) {
			tail.destroy();
		}
	}
}
