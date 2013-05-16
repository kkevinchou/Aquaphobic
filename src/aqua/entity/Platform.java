package aqua.entity;

public class Platform extends PhysEntity {
	public Platform(float x, float y, float width, float height) {
		super(x, y, width, height, CollisionType.RECTANGLE);
	}
	
	@Override
	public boolean skipCollisionResolution() {
		return true;
	}
}
