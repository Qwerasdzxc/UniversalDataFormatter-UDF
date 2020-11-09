package data_manipulation.finder.matchers.name_matchers;

public class EntityNameEndsWithMatcher extends EntityNameMatcher {

	public EntityNameEndsWithMatcher(String name, Object other) {
		super(name, other);
	}

	@Override
	public boolean matches() {
		try {
			String otherName = (String) other;
			return name.endsWith(otherName);
		} catch (Exception e) {
			return false;
		}
	}

}
