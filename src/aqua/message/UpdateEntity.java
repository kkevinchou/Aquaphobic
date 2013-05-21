package aqua.message;

import java.io.Serializable;

import org.newdawn.slick.geom.Shape;

import aqua.entity.BaseEntity;
import aqua.entity.PhysEntity;

public class UpdateEntity implements Serializable {
	private static final long serialVersionUID = -1458622942220279211L;
	public Shape shape;
	
	public UpdateEntity(BaseEntity entity) {
		shape = ((PhysEntity)entity).getCollisionShape();
	}
}
