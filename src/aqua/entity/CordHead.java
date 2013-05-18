package aqua.entity;

import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import knetwork.Constants;
import knetwork.message.AckMessage;
import knetwork.message.Message;
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

	public CordHead(float x, float y, Vector2D direction, Player owner) {
		super(x, y, radius, radius, CollisionType.CIRCLE);
		direction = direction.mult(baseSpeed);
		setSpeed(direction.getX(), direction.getY());
		this.owner = owner;
		
		targetOffsetX = 0;
		targetOffsetY = 0;
		
		collided = false;
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
		if (collided || owner.getId() == target.getId()
				|| !(target instanceof Player || target instanceof Platform)) {
			return;
		}
		
		collided = true;
		setSpeed(0, 0);
		
		if (target instanceof Platform) {
			Timer timer = new Timer();
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
//			((Player)target).applyEffect(pullEffect);
			target.attachment = this;
			
			tail = new CordTail(this, owner);
			PhysicsEngine.getInstance().queueAddition(tail);
			
			owner.onCordHitSuccessful(this.target);
		}
	}
	
	public void remove() {
		this.destroy();
		if (tail != null) {
			tail.destroy();
		}
	}
}
