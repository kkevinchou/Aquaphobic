package aqua.effect;

import aqua.entity.PhysEntity;
import aqua.physics.Vector2D;

public class PullEffect extends Effect {
	private float pullSpeed = 50;
	private Vector2D prevSpeed = Vector2D.ZERO;
	
	public PullEffect(PhysEntity owner, PhysEntity target) {
		super(owner, target);
	}
	
	@Override
	public void prePhysics() {
		target.incrementSpeed(-prevSpeed.getX(), -prevSpeed.getY());
		Vector2D separationVec = new Vector2D(owner.getX() - target.getX(), owner.getY() - target.getY());
		Vector2D appliedSpeed = separationVec.normalize().mult(pullSpeed);
		target.incrementSpeed(appliedSpeed.getX(), appliedSpeed.getY());
		prevSpeed = appliedSpeed;
	}
}
