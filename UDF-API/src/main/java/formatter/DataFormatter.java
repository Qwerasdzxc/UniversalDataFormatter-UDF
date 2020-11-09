package formatter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import configurator.UDFConfigurator;
import data_integrity.IdValidator;
import data_manipulation.finder.EntityFinder;
import data_manipulation.finder.FinderProperties;
import exceptions.IllegalIdentifierException;
import models.Entity;

public abstract class DataFormatter {

	private EntityFinder finder;

	public DataFormatter() {
		this.finder = new EntityFinder(this);
	}

	public boolean deleteEntity(Entity entityToDelete, boolean cascade) {
		List<Entity> entities = getAllEntities();

		if (cascade)
			entities.removeAll(entityToDelete.getChildren().values());

		for (Entity entity : entities) {
			if (entity.getChildren() != null && entity.getChildren().containsValue(entityToDelete)) {
				entity.getChildren().values().remove(entityToDelete);
				break;
			}
		}
			
		entities.remove(entityToDelete);

		try {
			saveAll(entities);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Entity createEntity(String name, Map<String, Object> attributes, Map<String, Entity> children)
			throws Exception {
		Entity entity = new Entity(generateId(), name, attributes, children);
		save(entity);

		return entity;
	}

	public Entity createEntity(int id, String name, Map<String, Object> attributes, Map<String, Entity> children)
			throws Exception {
		boolean idValid = verifyIdAvailable(id);

		if (!idValid)
			throw new IllegalIdentifierException();

		Entity entity = new Entity(id, name, attributes, children);
		save(entity);

		return entity;
	}

	public List<Entity> getAllEntities() {
		List<Entity> entities = new ArrayList<Entity>();
		String savePath = UDFConfigurator.getInstance().getSavePath();
		int fileCount = new File(savePath).list().length;

		for (int i = 0; i < fileCount; i++) {
			try {
				List<Entity> data = read(new File(savePath, "entities" + (i + 1) + getDataFormatExtension()));

				entities.addAll(data);
			} catch (Exception e) {
				entities.addAll(new ArrayList<Entity>());
			}
		}

		return entities;
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

	private boolean verifyIdAvailable(int id) {
		return IdValidator.verifyIdAvailable(id, getAllEntities());
	}

	private int generateId() {
		return IdValidator.generateId(getAllEntities());
	}

	private void save(Entity entity) throws Exception {
		List<Entity> entities = getAllEntities();

		entities.add(entity);
		saveAll(entities);
	}

	private void saveAll(List<Entity> entities) throws Exception {
		// Clear old files
		deleteAllEntityFiles();

		int fileNumber = 1;
		int entityLimitPerFile = UDFConfigurator.getInstance().getEntityLimitPerFile();
		for (int i = 0; i < entities.size(); i += entityLimitPerFile) {
			int end = i + entityLimitPerFile;
			save(entities.subList(i, end > entities.size() ? entities.size() : end), new File(
					UDFConfigurator.getInstance().getSavePath(), "entities" + fileNumber + getDataFormatExtension()));

			fileNumber++;
		}
	}

	private void deleteAllEntityFiles() {
		File saveDirectory = new File(UDFConfigurator.getInstance().getSavePath());
		for (File file : saveDirectory.listFiles())
			if (!file.isDirectory())
				file.delete();
	}

	abstract void save(List<Entity> entities, File file) throws Exception;

	abstract List<Entity> read(File file) throws Exception;

	abstract String getDataFormatExtension();

	abstract String getDataFormatName();
}
