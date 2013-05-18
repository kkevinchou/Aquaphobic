package aqua.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import aqua.effect.BroadEffects.BroadEffect;
import aqua.effect.Effect;
import aqua.physics.Vector2D;

public class Player extends PhysEntity {
	private float moveSpeed;
	private float jumpSpeed;
	
	private boolean isOnGround;
	
	private boolean movingLeft;
	private boolean movingRight;
	
	private boolean leftTrigger;
	private boolean rightTrigger;
	private boolean jumpTrigger;
	
	CordHead cordHead;
	
	private Set<BroadEffect> broadEffects;
	
	public Player(float x, float y, float width, float height) {
		super(x, y, width, height, CollisionType.RECTANGLE);
		moveSpeed = 200;
		jumpSpeed = 600;
		
		cordHead = null;
		
		isOnGround = false;
		
		movingLeft = false;
		movingRight = false;
		leftTrigger = false;
		rightTrigger = false;
		
		broadEffects = new HashSet<BroadEffect>();
	}
	
	public boolean getIsOnGround() {
		return isOnGround;
	}
	
	public void setIsOnGround(boolean state) {
		if (!isOnGround && state) {
			if (!leftTrigger && movingLeft) {
				incrementSpeed(moveSpeed, 0);
				movingLeft = false;
			}
			
			if (!rightTrigger && movingRight) {
				incrementSpeed(-moveSpeed, 0);
				movingRight = false;
			}
		}
		
		isOnGround = state;
		
		if (isOnGround && jumpTrigger) {
			jump();
		}
	}
	
	public void jump() {
		if (isOnGround) {
			setIsOnGround(false);
			incrementSpeed(0, -jumpSpeed);
		}
		
		jumpTrigger = true;
	}
	
	public void stopJump() {
		jumpTrigger = false;
	}
	
	public void moveLeft() {
		if (isOnGround && !movingLeft) {
			incrementSpeed(-moveSpeed, 0);
			movingLeft = true;
		} else {
			if (!movingLeft) {
				incrementSpeed(-moveSpeed, 0);
				movingLeft = true;
			}
			if (movingRight && !rightTrigger) {
				incrementSpeed(-moveSpeed, 0);
				movingRight = false;
			}
		}

		leftTrigger = true;
	}
	
	public void stopMoveLeft() {
		if (leftTrigger) {
			if (isOnGround || (!isOnGround && movingRight)) {
				incrementSpeed(moveSpeed, 0);
				movingLeft = false;
			}
		}
		
		leftTrigger = false;
	}
	
	public void moveRight() {
		if (isOnGround && !movingRight) {
			incrementSpeed(moveSpeed, 0);
			movingRight = true;
		} else {
			if (!movingRight) {
				incrementSpeed(moveSpeed, 0);
				movingRight = true;
			}
			if (movingLeft && !leftTrigger) {
				incrementSpeed(moveSpeed, 0);
				movingLeft = false;
			}
		}
		
		rightTrigger = true;
	}
	
	public void stopMoveRight() {
		if (rightTrigger) {
			if (isOnGround || (!isOnGround && movingLeft)) {
				incrementSpeed(-moveSpeed, 0);
				movingRight = false;
			}
		}
		
		rightTrigger = false;
	}
	
	public void launchCord(int targetX, int targetY) {
		if (broadEffects.contains(BroadEffect.DISARMED)) {
			return;
		}
		
		float playerCenterX = getX() + (getWidth()/2);
		float playerCenterY = getY() + (getHeight()/2);
		
		Vector2D direction = new Vector2D(targetX - playerCenterX, targetY - playerCenterY);
		direction = direction.normalize();
		
		cordHead = new CordHead(playerCenterX, playerCenterY, direction, this);
		EntityManager.getInstance().add(cordHead);
		
	}
	
	public void onCordHitSuccessful(Player target) {
		if (this.attachment != null) {
			target.destroyCord();
			this.attachment = null;
		}
	}
	
	public void destroyCord() {
		this.cordHead.remove();
		this.cordHead = null;
		broadEffects.remove(BroadEffect.DISARMED);
	}
	
	@Override
	public void performTimeStep(float elapsedTime) {
		super.performTimeStep(elapsedTime);
		if (cordHead == null) {
			broadEffects.remove(BroadEffect.DISARMED);
		} else {
			broadEffects.add(BroadEffect.DISARMED);
		}
	}
	
	@Override
	protected void onCollision(PhysEntity target) {
		if (target instanceof CordHead) {
			
		}
	}
	
	public String toString() {
		return "Player[id:" + id + "]";
	}
}
