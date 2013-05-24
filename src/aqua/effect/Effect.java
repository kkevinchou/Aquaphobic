package aqua.effect;

import aqua.entity.PhysEntity;

public abstract class Effect {
	protected PhysEntity owner;
	protected PhysEntity target;
	private static int nextId = 0;
	private int id;
	
	public Effect(PhysEntity owner, PhysEntity target) {
		id = nextId++;
		
		this.owner = owner;
		this.target = target;
	}
	
	public void onApply() {
		return;
	}
	
	public void prePhysics() {
		return;
	}
	
	public void postPhysics() {
		return;
	}
	
	public void onExpire() {
		return;
	}
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Effect)) {
			return false;
		}
		return id == ((Effect)other).id;
	}
}
