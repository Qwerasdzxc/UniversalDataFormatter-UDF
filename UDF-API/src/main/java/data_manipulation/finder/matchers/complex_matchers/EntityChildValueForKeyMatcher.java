package data_manipulation.finder.matchers.complex_matchers;

import data_manipulation.finder.EntityMatcher;
import data_manipulation.finder.NestedEntityAttributeFinder;
import models.Entity;

public class EntityChildValueForKeyMatcher implements EntityMatcher {
	
	private Entity entity;
	
	private Object value;
	
	public EntityChildValueForKeyMatcher(Entity entity, Object value) {
		this.entity = entity;
		this.value = value;
	}

	@Override
	public boolean matches() {
		try {
			NestedEntityAttributeFinder data = (NestedEntityAttributeFinder) value;
			
			if (entity == null || entity.getAttributes() == null)
				return false;
			
			if (!entity.getAttributes().containsKey(data.getChildAttributeKey()))
				return false;
			
			return entity.getAttributes().get(data.getChildAttributeKey()).equals(data.getChildAttributeValue());
		} catch (Exception e) {
			return false;
		}
	}

}
