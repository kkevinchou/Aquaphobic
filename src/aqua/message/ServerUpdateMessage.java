package aqua.message;

import java.util.ArrayList;
import java.util.List;

import aqua.entity.BaseEntity;

import knetwork.message.Message;

public class ServerUpdateMessage extends Message {
	private static final long serialVersionUID = 3533954835851655367L;

	public List<UpdateEntity> entities;
	
	public ServerUpdateMessage(List<BaseEntity> entities) {
		
		this.entities = new ArrayList<UpdateEntity>(4);
		if (entities == null) {
			return;
		}
		for (BaseEntity entity : entities) {
			this.entities.add(new UpdateEntity(entity));
		}
	}
}
