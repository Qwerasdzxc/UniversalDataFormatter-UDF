package data_integrity;

import java.util.List;

import models.Entity;

public class IdValidator {

	public static int generateId(List<Entity> entities) {
		int maxId = -1;
		
		for (Entity entity : entities) {
			if (entity.getId() > maxId)
				maxId = entity.getId();
			
			if (entity.getChildren() != null) {
				for (Entity child : entity.getChildren().values())
					if (child.getId() > maxId)
						maxId = child.getId();
			}
		}
		
		return maxId + 1;
	}
	
	public static boolean verifyIdAvailable(int id, List<Entity> entities) {
		boolean available = true;
		
		for (Entity entity : entities) {
			if (entity.getId() == id)
				available = false;
			
			if (entity.getChildren() != null) {
				for (Entity child : entity.getChildren().values())
					if (child.getId() == id)
						available = false;
			}
		}
		
		return available;
	}
}
