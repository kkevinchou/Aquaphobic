package aqua.entity;

public class Player extends PhysEntity {
	private float moveSpeed;
	
	public Player(float x, float y, float width, float height) {
		super(x, y, width, height);
		moveSpeed = 200;
	}
	
	public void jump() {
		if (onGround) {
		}
	}
	
	public void moveRight() {
		incrementSpeed(moveSpeed, 0);
	}
	
	public void moveLeft() {
		incrementSpeed(-moveSpeed, 0);
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
