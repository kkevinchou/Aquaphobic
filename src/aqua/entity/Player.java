package aqua.entity;

import java.util.ArrayList;
import java.util.List;

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
	
	public Player(float x, float y, float width, float height) {
		super(x, y, width, height, CollisionType.RECTANGLE);
		moveSpeed = 200;
		jumpSpeed = 600;
		
		isOnGround = false;
		
		movingLeft = false;
		movingRight = false;
		leftTrigger = false;
		rightTrigger = false;
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
		float playerCenterX = getX() + (getWidth()/2);
		float playerCenterY = getY() + (getHeight()/2);
		
		Vector2D direction = new Vector2D(targetX - playerCenterX, targetY - playerCenterY);
		direction = direction.normalize();
		
		CordHead c = new CordHead(playerCenterX, playerCenterY, direction, this);
		EntityManager.getInstance().add(c);
		
	}
	
	public void onCordHitSuccessful() {
		if (this.attachment != null) {
			((CordHead)this.attachment).remove();
		}
	}
	
	public String toString() {
		return "Player[id:" + id + "]";
	}
}
