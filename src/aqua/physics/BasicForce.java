package aqua.physics;

import aqua.entity.PhysEntity;

public class BasicForce extends Force {
	public BasicForce(PhysEntity target, float x, float y) {
		super(target, x, y);
	}
	
	public BasicForce(PhysEntity target, Vector2D forceVec) {
		super(target, forceVec);
	}
}
