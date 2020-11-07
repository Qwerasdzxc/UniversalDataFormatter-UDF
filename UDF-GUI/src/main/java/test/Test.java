package test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import formatter.DataFormatter;
import manager.UDFManager;
import models.Entity;

public class Test {

	public static void main(String[] args) {
		try {
			Class.forName("formatter.FormatterImpl");
			
			DataFormatter formatter = UDFManager.getFormatter();
			HashMap<String, Object> attributes = new HashMap<String, Object>();
			attributes.put("year", 21);
			attributes.put("name", "Luka");
			
			Entity child = new Entity(2, "Person", attributes, null);
			HashMap<String, Entity> children = new HashMap<String, Entity>();
			children.put("child", child);
			
			Entity entity = new Entity(1, "Person", attributes, children);
			
			List<Entity> entities = new ArrayList<Entity>();
			entities.add(entity);
			entities.add(child);
			
			String projectPath = System.getProperty("user.dir");
			String savePath = projectPath + "/src/main/resources";
			File file = new File(savePath);
			System.out.println(savePath);
			
			formatter.save(entities, file);
			
			List<Entity> output = formatter.read(file);
			
			for (Entity e : output) {
				System.out.println(e.toString());
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
