package main;

import app.UDFApplication;

public class Main {

	public static void main(String[] args) {
		try {
			Class.forName("formatter.FormatterImpl");
			UDFApplication.start();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
//		try {
//			Class.forName("formatter.FormatterImpl");
//			
//			DataFormatter formatter = UDFManager.getFormatter();
//			HashMap<String, Object> attributes = new HashMap<String, Object>();
//			attributes.put("year", 21);
//			attributes.put("name", "Luka");
//			
//			Entity child = new Entity(2, "Person", attributes, null);
//			HashMap<String, Entity> children = new HashMap<String, Entity>();
//			children.put("child", child);
//			
//			Entity entity = new Entity(1, "Person", attributes, children);
//			
//			List<Entity> entities = new ArrayList<Entity>();
//			entities.add(entity);
//			entities.add(child);
//			
//			String projectPath = System.getProperty("user.dir");
//			String savePath = projectPath + "/src/main/resources";
//			File file = new File(savePath);
//			System.out.println(savePath);
//			
//			formatter.save(entities, file);
//			
//			List<Entity> output = formatter.read(file);
//			
//			for (Entity e : output) {
//				System.out.println(e.toString());
//			}
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

}
