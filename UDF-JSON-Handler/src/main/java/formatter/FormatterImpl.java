package formatter;

import java.io.File;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import manager.UDFManager;
import models.Entity;

public class FormatterImpl extends DataFormatter {
	
	static {
		UDFManager.registerFormatter(new FormatterImpl());
	}

	@Override
	public void save(List<Entity> entities, File file) throws Exception {
		File jsonFile = new File(file, "output.json");
		new ObjectMapper().writeValue(jsonFile, entities);
	}

	@Override
	public List<Entity> read(File file) throws Exception {
		File jsonFile = new File(file, "output.json");
		List<Entity> entities = new ObjectMapper().readValue(jsonFile, new TypeReference<List<Entity>>() {});

		return entities;
	}
}
