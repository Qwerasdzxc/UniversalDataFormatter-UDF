package formatter;

import java.io.File;
import java.util.List;
import java.util.Map;

import formatter.configurator.UDFConfigurator;
import formatter.data_manipulation.finder.EntityFinder;
import formatter.data_manipulation.finder.FinderProperties;
import formatter.models.Entity;

public abstract class DataFormatter {

	private EntityFinder finder;
	private EntityPersister persister;

	public DataFormatter() {
		this.finder = new EntityFinder(this);
		this.persister = new EntityPersister(this);
	}

	public boolean deleteEntity(Entity entityToDelete, boolean cascade) {
		return persister.deleteEntity(entityToDelete, cascade);
	}

	public Entity createEntity(String name, Map<String, Object> attributes, Map<String, Entity> children)
			throws Exception {
		return persister.createEntity(name, attributes, children);
	}

	public Entity createEntity(int id, String name, Map<String, Object> attributes, Map<String, Entity> children)
			throws Exception {
		return persister.createEntity(id, name, attributes, children);
	}

	public List<Entity> getAllEntities() {
		return persister.getAllEntities();
	}

	public List<Entity> searchForEntities(Map<FinderProperties, Object> searchData) {
		return finder.getEntities(searchData);
	}

	public int getEntityLimitPerFile() {
		return UDFConfigurator.getInstance().getEntityLimitPerFile();
	}

	public void setEntityLimitPerFile(int entityLimitPerFile) {
		UDFConfigurator.getInstance().setEntityLimitPerFile(entityLimitPerFile);
	}

	public String getInfoText() {
		return "Currently wokring with " + getDataFormatName() + " files using " + getDataFormatExtension()
				+ " extension.";
	}

	abstract void save(List<Entity> entities, File file) throws Exception;

	abstract List<Entity> read(File file) throws Exception;

	abstract String getDataFormatExtension();

	abstract String getDataFormatName();
}
