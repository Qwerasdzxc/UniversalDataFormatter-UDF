package data_integrity;

import java.util.List;

import models.Entity;

public class IdValidator {

	public int generateId(List<Entity> entities) {
		int maxId = -1;
		
		for (Entity entity : entities) {
			if (entity.getId() > maxId)
				maxId = entity.getId();
			
			for (Entity child : entity.getChildren().values())
				if (child.getId() > maxId)
					maxId = child.getId();
		}
		
		return maxId + 1;
	}
	
	public boolean verifyIdAvailable(int id, List<Entity> entities) {
		boolean available = true;
		
		for (Entity entity : entities) {
			if (entity.getId() == id)
				available = false;
			
			for (Entity child : entity.getChildren().values())
				if (child.getId() == id)
					available = false;
		}
		
		return available;
	}
}
