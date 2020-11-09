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
	
	public void createEntity(String name, Map<String, Object> attributes, Map<String, Entity> children) throws Exception {
		Entity entity = new Entity(generateId(), name, attributes, children);
		
		save(entity);
	}
	
	public void createEntity(int id, String name, Map<String, Object> attributes, Map<String, Entity> children) throws Exception {
		boolean idValid = verifyIdAvailable(id);
		
		if (!idValid)
			throw new IllegalIdentifierException();
		
		Entity entity = new Entity(id, name, attributes, children);
		
		save(entity);
	}
	
	public List<Entity> getAllEntities() {
		List<Entity> entities = new ArrayList<Entity>();
		String savePath = UDFConfigurator.getInstance().getSavePath();
		int fileCount = new File(savePath).list().length;
		
		for (int i = 0; i < fileCount; i++) {
			try {
				List<Entity> data = read(new File(savePath, "entities" + (i + 1) + getDataFormatExtension()));
				List<Entity> children = new ArrayList<Entity>();
				
				for (Entity entity : data) {
					if (entity.getChildren() == null)
						continue;
					
					children.addAll(entity.getChildren().values());
				}
				
				data.addAll(children);
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
	
	private boolean verifyIdAvailable(int id) {
		return IdValidator.verifyIdAvailable(id, getAllEntities());
	}
	
	private int generateId() {
		return IdValidator.generateId(getAllEntities());
	}
	
	private void save(Entity entity) throws Exception {
		List<Entity> entities = getAllEntities();
		
		entities.add(entity);
		
		int fileNumber = 1;
		int entityLimitPerFile = UDFConfigurator.getInstance().getEntityLimitPerFile();
		for (int i = 0; i < entities.size(); i += entityLimitPerFile) {
			int end = i + entityLimitPerFile;
			save(entities.subList(i, end > entities.size() ? entities.size() : end), 
					new File(UDFConfigurator.getInstance().getSavePath(), "entities" + fileNumber + getDataFormatExtension()));
			
			fileNumber ++;
		}
	}
	
	abstract void save(List<Entity> entities, File file) throws Exception;
	
	abstract List<Entity> read(File file) throws Exception;
	
	abstract String getDataFormatExtension();
}
