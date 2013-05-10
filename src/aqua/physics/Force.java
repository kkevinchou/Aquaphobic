package aqua.physics;

public class Force {
	private static int nextId = 0;
	private int id;
	
	private float x;
	private float y;
	
	public Force(float x, float y) {
		this.x = x;
		this.y = y;
		
		id = nextId++;
	}
	
	public int getId() {
		return id;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public Vector2D getVector() {
		return new Vector2D(x, y);
	}
}
