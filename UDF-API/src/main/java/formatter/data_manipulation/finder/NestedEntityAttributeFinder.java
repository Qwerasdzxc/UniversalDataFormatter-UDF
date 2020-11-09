package formatter.data_manipulation.finder;

public class NestedEntityAttributeFinder {
	
	private String parentKey;
	private String childAttributeKey;
	
	private Object childAttributeValue;

	public NestedEntityAttributeFinder(String parentKey, String childAttributeKey, Object childValue) {
		this.parentKey = parentKey;
		this.setChildAttributeKey(childAttributeKey);
		this.setChildAttributeValue(childValue);
	}

	public String getParentKey() {
		return parentKey;
	}

	public void setParentKey(String parentKey) {
		this.parentKey = parentKey;
	}

	public String getChildAttributeKey() {
		return childAttributeKey;
	}

	public void setChildAttributeKey(String childAttributeKey) {
		this.childAttributeKey = childAttributeKey;
	}

	public Object getChildAttributeValue() {
		return childAttributeValue;
	}

	public void setChildAttributeValue(Object childAttributeValue) {
		this.childAttributeValue = childAttributeValue;
	}
}
