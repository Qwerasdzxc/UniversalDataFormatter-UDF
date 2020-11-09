package formatter.data_manipulation.finder.matchers.name_matchers;

import formatter.data_manipulation.finder.EntityMatcher;

public abstract class EntityNameMatcher implements EntityMatcher {
	
	protected String name;
	
	protected Object other;
	
	public EntityNameMatcher(String name, Object other) {
		this.name = name;
		this.other = other;
	}
}
