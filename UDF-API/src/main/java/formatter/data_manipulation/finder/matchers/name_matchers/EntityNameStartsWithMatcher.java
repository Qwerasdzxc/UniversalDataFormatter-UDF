package formatter.data_manipulation.finder.matchers.name_matchers;

public class EntityNameStartsWithMatcher extends EntityNameMatcher {

	public EntityNameStartsWithMatcher(String name, Object other) {
		super(name, other);
	}

	@Override
	public boolean matches() {
		try {
			String otherName = (String) other;
			return name.startsWith(otherName);
		} catch (Exception e) {
			return false;
		}
	}
}
