package formatter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import data_integrity.IdValidator;
import models.Entity;

public abstract class DataFormatter {
	
	final static String savePath = System.getProperty("user.dir") + "/src/main/resources";
	
	private int entityLimitPerFile = 3;
	
	private IdValidator idValidator;
	
	public DataFormatter() {
		this.idValidator = new IdValidator();
	}
	
	public void save(Entity entity) throws Exception {
		List<Entity> entities = getAllEntities();
		
		entities.add(entity);
		
		int fileNumber = 1;
		for (int i = 0; i < entities.size(); i += entityLimitPerFile) {
			int end = i + entityLimitPerFile;
			save(entities.subList(i, end > entities.size() ? entities.size() : end), 
					new File(savePath, "entities" + fileNumber + getDataFormatExtension()));
			
			fileNumber ++;
		}
	}
	
	public List<Entity> getAllEntities() {
		List<Entity> entities = new ArrayList<Entity>();
		int fileCount = new File(savePath).list().length;
		
		for (int i = 0; i < fileCount; i++) {
			try {
				entities.addAll(read(new File(savePath, "entities" + (i + 1) + getDataFormatExtension())));
			} catch (Exception e) {
				entities.addAll(new ArrayList<Entity>());
			}
		}
		
		return entities;
	}
	
	public int getEntityLimitPerFile() {
		return entityLimitPerFile;
	}

	public void setEntityLimitPerFile(int entityLimitPerFile) {
		this.entityLimitPerFile = entityLimitPerFile;
	}
	
	public boolean verifyIdAvailable(int id) {
		return idValidator.verifyIdAvailable(id, getAllEntities());
	}
	
	abstract void save(List<Entity> entities, File file) throws Exception;
	
	abstract List<Entity> read(File file) throws Exception;
	
	abstract String getDataFormatExtension();
}
