package aqua.entity;

import java.util.ArrayList;
import java.util.List;

public class EntityManager {
	private List<BaseEntity> entities;
	
	public EntityManager() {
		entities = new ArrayList<BaseEntity>();
	}
	
	public void add(BaseEntity entity) {
		entities.add(entity);
	}
	
	public void remove(int id) {
		int removeIndex = -1;
		
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).getId() == id) {
				removeIndex = i;
				break;
			}
		}
		
		if (removeIndex != -1) {
			entities.remove(removeIndex);
		}
	}
	
	public void remove(BaseEntity entity) {
		entities.remove(entity);
	}
	
	public BaseEntity getEntityById(int id) {
		for (BaseEntity entity : entities) {
			if (entity.getId() == id) {
				return entity;
			}
		}
		return null;
	}
	
	public List<BaseEntity> getEntities() {
		return entities;
	}
}
