package formatter.data_manipulation.finder.matchers.id_matchers;

import formatter.data_manipulation.finder.EntityMatcher;

public class EntityIdMatcher implements EntityMatcher {
	
	protected int id; 
	
	protected Object other;

	public EntityIdMatcher(int id, Object other) {
		this.id = id;
		this.other = other;
	}
	
	@Override
	public boolean matches() {
		try {
			int otherId = (int) other;
			return id == otherId;
		} catch (Exception e) {
			return false;
		}
	}
}
