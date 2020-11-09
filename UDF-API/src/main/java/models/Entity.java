package models;

import java.util.Map;
import java.util.Objects;

public class Entity {

	private int id;

	private String name;

	private Map<String, Object> attributes;
	private Map<String, Entity> children;

	public Entity() {
	}

	public Entity(int id, String name, Map<String, Object> attributes, Map<String, Entity> children) {
		this.id = id;
		this.name = name;
		this.attributes = attributes;
		this.children = children;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public Map<String, Entity> getChildren() {
		return children;
	}

	public void setChildren(Map<String, Entity> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return "Entity [id=" + id + ", name=" + name + ", attributes=" + attributes + ", children=" + children + "]";
	}

	@Override
	public boolean equals(Object o) {

		if (o == this)
			return true;
		if (!(o instanceof Entity)) {
			return false;
		}
		Entity entity = (Entity) o;

		return id == entity.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
