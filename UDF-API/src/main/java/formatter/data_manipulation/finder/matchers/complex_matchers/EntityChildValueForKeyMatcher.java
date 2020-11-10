package formatter.data_manipulation.finder.matchers.complex_matchers;

import formatter.data_manipulation.finder.EntityMatcher;
import formatter.data_manipulation.finder.NestedEntityAttributeFinder;
import formatter.models.Entity;

public class EntityChildValueForKeyMatcher implements EntityMatcher {
	
	private Entity parent;
	
	private Object value;
	
	public EntityChildValueForKeyMatcher(Entity parent, Object value) {
		this.parent = parent;
		this.value = value;
	}

	@Override
	public boolean matches() {
		try {
			if (parent.getChildren() == null)
				return false;
			
			NestedEntityAttributeFinder data = (NestedEntityAttributeFinder) value;
			Entity child = parent.getChildren().get(data.getParentKey());
			
			if (child == null || child.getAttributes() == null)
				return false;
			
			if (!child.getAttributes().containsKey(data.getChildAttributeKey()))
				return false;
			
			return child.getAttributes().get(data.getChildAttributeKey()).equals(data.getChildAttributeValue());
		} catch (Exception e) {
			return false;
		}
	}

}
