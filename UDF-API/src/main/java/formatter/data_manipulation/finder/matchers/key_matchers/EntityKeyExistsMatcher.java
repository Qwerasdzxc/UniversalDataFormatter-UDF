package formatter.data_manipulation.finder.matchers.key_matchers;

import java.util.Set;

import formatter.data_manipulation.finder.EntityMatcher;

public class EntityKeyExistsMatcher implements EntityMatcher {

	protected Set<String> keys;
	
	protected Object other;

	public EntityKeyExistsMatcher(Set<String> keys, Object searchKey) {
		this.keys = keys;
		this.other = searchKey;
	}

	@Override
	public boolean matches() {
		try {
			String searchKey = (String) other;
			boolean keyExists = false;

			for (String key : keys) {
				if (key.equals(searchKey))
					keyExists = true;
			}
			
			return keyExists;
		} catch (Exception e) {
			return false;
		}
	}
}
