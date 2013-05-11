package aqua.physics;

import aqua.entity.PhysEntity;

public abstract class Force {
	private static int nextId = 0;
	private int id;
	
	private float x;
	private float y;
	
	private PhysEntity target;
	
	public Force(PhysEntity target, float x, float y) {
		this.x = x;
		this.y = y;
		
		id = nextId++;
		this.target = target;
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
	
	public void postResolutionMethod() {
		return;
	}
	
	public boolean equals(Force force) {
		return this.id == force.getId();
	}
}
