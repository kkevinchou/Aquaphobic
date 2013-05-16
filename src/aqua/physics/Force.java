package aqua.physics;

import aqua.entity.PhysEntity;

public abstract class Force {
	private static int nextId = 0;
	private int id;
	
	private float x;
	private float y;
	
	private PhysEntity target;
	
	private void init(PhysEntity target, float x, float y) {
		this.x = x;
		this.y = y;
		
		id = nextId++;
		this.target = target;
	}
	
	public Force(PhysEntity target, float x, float y) {
		init(target, x, y);
	}
	
	public Force(PhysEntity target, Vector2D forceVec) {
		init(target, forceVec.getX(), forceVec.getY());
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
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public void setForceVec(Vector2D forceVec) {
		x = forceVec.getX();
		y = forceVec.getY();
	}
	
	public Vector2D getVector() {
		return new Vector2D(x, y);
	}
	
	public boolean equals(Force force) {
		return this.id == force.getId();
	}
}
