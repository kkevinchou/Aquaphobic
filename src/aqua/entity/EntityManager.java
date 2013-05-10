package aqua.entity;

import java.util.ArrayList;
import java.util.List;

public class EntityManager {
	private static EntityManager instance = new EntityManager();
	private List<BaseEntity> entities;
	
	private EntityManager() {
		entities = new ArrayList<BaseEntity>();
	}
	
	public static EntityManager getInstance() {
		return instance;
	}
	
	public void add(BaseEntity entity) {
		entities.add(entity);
	}
	
	public void remove(int id) {
		entities.remove(id);
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
