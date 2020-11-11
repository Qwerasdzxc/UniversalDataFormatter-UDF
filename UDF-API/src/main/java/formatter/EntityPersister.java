package formatter;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import formatter.data_integrity.IdValidator;
import formatter.exceptions.IllegalIdentifierException;
import formatter.models.Entity;

/**
 * Persister for doing CRUD operation on {@link formatter.models.Entity}s
 */
class EntityPersister {

	/**
	 * Active {@link formatter.DataFormatter}
	 */
	private DataFormatter formatter;

	/**
	 * Main constructor for initialization
	 */
	public EntityPersister(DataFormatter formatter) {
		this.formatter = formatter;
	}

	/**
	 * <p>Deletes the given {@link formatter.models.Entity} from storage</p>
	 * @param entityToDelete 	entity for deletion
	 * @param cascade	when cascade is active, all Entity's children will be deleted too
	 * @return success	whether the operation succeeded
	 */
	public boolean deleteEntity(Entity entityToDelete, boolean cascade) {
		List<Entity> entities = getAllEntities();

		// Move children to the root level
		if (!cascade && entityToDelete.getChildren() != null) {
			List<Entity> children = new ArrayList<Entity>();

			for (Entity entity : entities) {
				if (entity.getChildren() == null)
					continue;

				children.addAll(entity.getChildren().values());
			}
			entities.addAll(children);
		}

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

	/**
	 * <p>Creates an {@link formatter.models.Entity} from parameters and auto-generates unique identifier</p>
	 * @param name			entity name
	 * @param attributes	entity attributes, can be null
	 * @param children		entity children, can be null
	 * @return success		whether the operation succeeded
	 * @throws Exception	error occurred during creation
	 */
	public Entity createEntity(String name, Map<String, Object> attributes, Map<String, Entity> children)
			throws Exception {
		int lastGeneratedId = generateId();
		Entity entity = new Entity(lastGeneratedId, name, attributes, children);
		
		if (children != null) {
			Collection<Entity> childrenEntities = children.values();
			for (Entity child : childrenEntities) {
				child.setId(lastGeneratedId + 1);
				lastGeneratedId ++;
			}
		}
		
		save(entity);

		return entity;
	}

	/**
	 * <p>Creates an {@link formatter.models.Entity} from parameters and checks unique identifier's validity</p>
	 * @param id			entity unique identifier
	 * @param name			entity name
	 * @param attributes	entity attributes, can be null
	 * @param children		entity children, can be null
	 * @return success		whether the operation succeeded
	 * @throws Exception	error occurred during creation
	 */
	public Entity createEntity(int id, String name, Map<String, Object> attributes, Map<String, Entity> children)
			throws Exception {
		boolean idValid = verifyIdAvailable(id);

		if (!idValid)
			throw new IllegalIdentifierException();
		
		if (children != null) {
			Collection<Entity> childrenEntities = children.values();
			for (Entity child : childrenEntities) {
				if (child.getId() == id || !verifyIdAvailable(child.getId()))
					throw new IllegalIdentifierException();
			}
		}

		Entity entity = new Entity(id, name, attributes, children);
		save(entity);

		return entity;
	}
	
	/**
	 * <p>Updates an existing {@link formatter.models.Entity}</p>
	 * @param entity	updated entity instance
	 * @throws Exception	error occurred during creation
	 */
	public void updateEntity(Entity entity) throws Exception {
		List<Entity> entities = getAllEntities();
		int indexToUpdate = 0;
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).getId() == entity.getId())
				indexToUpdate = i;
		}
		
		entities.set(indexToUpdate, entity);
		saveAll(entities);
	}

	/**
	 * <p>Returns all {@link formatter.models.Entity}s from storage</p>
	 * @return entities	entities from storage
	 */
	public List<Entity> getAllEntities() {
		List<Entity> entities = new ArrayList<Entity>();
		String savePath = UDFConfigurator.getInstance().getSavePath();
		String[] files = new File(savePath).list();
		
		if (files == null)
			return entities;
		
		int fileCount = files.length;

		for (int i = 0; i < fileCount; i++) {
			try {
				List<Entity> data = formatter
						.read(new File(savePath, "entities" + (i + 1) + formatter.getDataFormatExtension()));
				entities.addAll(data);
			} catch (Exception e) {
				entities.addAll(new ArrayList<Entity>());
			}
		}

		return entities;
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
			formatter.save(entities.subList(i, end > entities.size() ? entities.size() : end),
					new File(UDFConfigurator.getInstance().getSavePath(),
							"entities" + fileNumber + formatter.getDataFormatExtension()));

			fileNumber++;
		}
	}

	private void deleteAllEntityFiles() {
		File saveDirectory = new File(UDFConfigurator.getInstance().getSavePath());
		for (File file : saveDirectory.listFiles())
			if (!file.isDirectory())
				file.delete();
	}
}
