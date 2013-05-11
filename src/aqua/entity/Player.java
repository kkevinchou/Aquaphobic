package aqua.entity;

public class Player extends PhysEntity {
	private float moveSpeed;
	private float jumpSpeed;
	
	private boolean isOnGround;
	
	public boolean movingLeft;
	public boolean movingRight;
	
	private boolean leftTrigger;
	private boolean rightTrigger;
	
	
	public Player(float x, float y, float width, float height) {
		super(x, y, width, height);
		moveSpeed = 200;
		jumpSpeed = 400;
		
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
		if (!isOnGround && state && (movingLeft || movingRight)) {
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
	}
	
	public void jump() {
		if (isOnGround) {
			setIsOnGround(false);
			incrementSpeed(0, -jumpSpeed);
		}
	}
	
	public void moveLeft() {
		if (isOnGround) {
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
		if (leftTrigger && isOnGround) {
			incrementSpeed(moveSpeed, 0);
			movingLeft = false;
		}
		else if (leftTrigger && !isOnGround && movingRight) {
			incrementSpeed(moveSpeed, 0);
			movingLeft = false;
		}
		
		leftTrigger = false;
	}
	
	public void moveRight() {
		if (isOnGround) {
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
		if (rightTrigger && isOnGround) {
			incrementSpeed(-moveSpeed, 0);
			movingRight = false;
		}
		else if (rightTrigger && !isOnGround && movingLeft) {
			incrementSpeed(-moveSpeed, 0);
			movingRight = false;
		}
		
		rightTrigger = false;
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
