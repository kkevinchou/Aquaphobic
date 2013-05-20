package aqua.entity;

import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Line;

public class CordTail extends PhysEntity {
	
	public Player owner;
	public CordHead head;

	public CordTail(CordHead head, Player owner) {
		super(0, 0, 0, 0, CollisionType.LINE);
		
		this.owner = owner;
		this.head = head;
		
		Circle headCollisionShape = (Circle)head.getCollisionShape();
		collisionShape = new Line(owner.getX() + owner.getWidth()/2, owner.getY() + owner.getHeight()/2, headCollisionShape.getCenterX(), headCollisionShape.getCenterY());
	}
	
	public void performTimeStep(float elapsedTime) {
		Line line = (Line)collisionShape;
		Circle headCollisionShape = (Circle)head.getCollisionShape();
		
		line.set(owner.getX() + owner.getWidth()/2, owner.getY() + owner.getHeight()/2, headCollisionShape.getCenterX(), headCollisionShape.getCenterY());
	}
	
	@Override
	public void onCollision(PhysEntity target) {
		if (target instanceof CordTail) {
			((CordTail)target).owner.destroyCord();
			owner.destroyCord();
		}
	}
}
