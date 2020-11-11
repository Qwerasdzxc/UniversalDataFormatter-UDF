package formatter.data_manipulation.finder.matchers.id_matchers;

import formatter.data_manipulation.finder.EntityMatcher;

public class EntityIdMatcher implements EntityMatcher {
	
	private int id; 
	
	private Object other;

	public EntityIdMatcher(int id, Object other) {
		this.id = id;
		this.other = other;
	}
	
	@Override
	public boolean matches() {
		try {
			int otherId = Integer.parseInt((String) other);
			return id == otherId;
		} catch (Exception e) {
			return false;
		}
	}
}
