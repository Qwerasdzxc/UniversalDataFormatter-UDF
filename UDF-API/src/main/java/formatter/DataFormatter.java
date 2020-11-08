package formatter;

import java.io.File;
import java.util.List;

import models.Entity;

public abstract class DataFormatter {
	
	String savePath = System.getProperty("user.dir") + "/src/main/resources";

	public DataFormatter() {}
	
	public void save(List<Entity> entities) throws Exception {
		save(entities, new File(savePath));
	}
	
	public List<Entity> read() throws Exception {
		return read(new File(savePath));
	}
	
	abstract void save(List<Entity> entities, File folder) throws Exception;
	
	abstract List<Entity> read(File folder) throws Exception;
}
