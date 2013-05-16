package aqua.effect;

import aqua.entity.PhysEntity;

public abstract class Effect {
	protected PhysEntity owner;
	protected PhysEntity target;
	
	public Effect(PhysEntity owner, PhysEntity target) {
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
}
