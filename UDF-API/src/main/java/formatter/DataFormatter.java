package formatter;

import java.io.File;
import java.util.List;

import models.Entity;

public abstract class DataFormatter {

	public DataFormatter() {}
	
	public abstract void save(List<Entity> entities, File folder) throws Exception;
	
	public abstract List<Entity> read(File folder) throws Exception;
}
