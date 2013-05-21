package aqua.entity;

import java.io.Serializable;

public abstract class BaseEntity implements Serializable {
	private static final long serialVersionUID = 7524134163491422371L;
	
	public int id;
	protected float x;
	protected float y;
	protected float width;
	protected float height;

	public static int nextId = 0;
	
	public BaseEntity(float x, float y, float width, float height) {
		this.id = nextId++;
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public int getId() {
		return id;
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}
	
	public void destroy() {
		return;
	}
	
	public boolean equals(Object entity) {
		if (!(entity instanceof BaseEntity)) {
			return false;
		}
		return ((BaseEntity)entity).getId() == getId();
	}
	
	public String toString() {
		return "BaseEntity[id: " + id + "]";
	}
}