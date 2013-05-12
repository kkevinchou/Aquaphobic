package aqua.entity;

import org.newdawn.slick.geom.Circle;

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
		Vector2D direction = new Vector2D(targetX - getX(), targetY - getY());
		direction = direction.normalize();
		
		CordHead c = new CordHead(getX() + (getWidth()/2), getY() + (getHeight()/2), direction, this);
		EntityManager.getInstance().add(c);
		
	}
	
	public void stop() {
		setSpeed(0, 0);
	}
	
	public boolean isPlayer() {
		return true;
	}
	
	public String toString() {
		return "Player[id:" + id + "]";
	}
}
