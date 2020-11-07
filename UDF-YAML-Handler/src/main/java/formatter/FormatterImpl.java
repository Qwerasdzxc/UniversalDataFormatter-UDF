package formatter;

import java.io.File;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator.Feature;

import manager.UDFManager;
import models.Entity;

public class FormatterImpl extends DataFormatter {

	static {
		UDFManager.registerFormatter(new FormatterImpl());
	}

	@Override
	public void save(List<Entity> entities, File folder) throws Exception {
		File yamlFile = new File(folder, "output.yaml");
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory().disable(Feature.WRITE_DOC_START_MARKER));
		mapper.writeValue(yamlFile, entities);
	}

	@Override
	public List<Entity> read(File folder) throws Exception {
		File yamlFile = new File(folder, "output.yaml");
		ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
		List<Entity> entities = objectMapper.readValue(yamlFile, new TypeReference<List<Entity>>() {});

		return entities;
	}
}
