package formatter;

import java.io.File;
import java.util.List;

import manager.UDFManager;
import models.Entity;

public class FormatterImpl extends DataFormatter {

	static {
		UDFManager.registerFormatter(new FormatterImpl());
	}

	@Override
	public void save(List<Entity> entities, File folder) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Entity> read(File folder) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
