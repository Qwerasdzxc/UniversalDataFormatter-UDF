package formatter.data_manipulation.finder.matchers.name_matchers;

public class EntityNameEqualsMatcher extends EntityNameMatcher {

	public EntityNameEqualsMatcher(String name, Object other) {
		super(name, other);
	}

	@Override
	public boolean matches() {
		try {
			String otherName = (String) other;
			return name.equals(otherName);
		} catch (Exception e) {
			return false;
		}
	}
}
