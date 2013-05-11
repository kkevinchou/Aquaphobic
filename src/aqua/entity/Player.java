package aqua.entity;

public class Player extends PhysEntity {
	private float moveSpeed;
	private float jumpSpeed;
	
	private boolean isOnGround;
	
	public boolean movingLeft;
	public boolean movingRight;
	public boolean fallingLeft;
	public boolean fallingRight;
	
	public Player(float x, float y, float width, float height) {
		super(x, y, width, height);
		moveSpeed = 200;
		jumpSpeed = 400;
		
		isOnGround = false;
		
		movingLeft = false;
		movingRight = false;
		fallingRight = false;
		fallingRight = false;
	}
	
	public boolean getIsOnGround() {
		return isOnGround;
	}
	
	public void setIsOnGround(boolean state) {
		if (!isOnGround && state && (fallingLeft || fallingRight)) {
			setXSpeed(0);
			fallingRight = false;
			fallingLeft = false;
		}
		isOnGround = state;
	}
	
	public void jump() {
		if (isOnGround) {
			incrementSpeed(0, -jumpSpeed);
		}
	}
	
	public void moveRight() {
		if (!fallingRight && !fallingLeft) {
			incrementSpeed(moveSpeed, 0);
			movingRight = true;
		}
	}
	
	public void stopMoveRight() {
		if (movingRight) {
			if (isOnGround) {
				incrementSpeed(-moveSpeed, 0);
			} else {
				fallingRight = true;
			}
			movingRight = false;
		}
	}
	
	public void moveLeft() {
		if (!fallingLeft && !fallingRight) {
			incrementSpeed(-moveSpeed, 0);
			movingLeft = true;
		}
	}
	
	public void stopMoveLeft() {
		if (movingLeft) {
			if (isOnGround) {
				incrementSpeed(moveSpeed, 0);
			} else {
				fallingLeft = true;
			}
			movingLeft = false;
		}
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
