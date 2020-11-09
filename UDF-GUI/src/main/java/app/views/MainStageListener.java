package app.views;

import java.util.List;

import models.Entity;

public interface MainStageListener {
	
	public List<Entity> getTableEntities();

	public List<Entity> getSelectedEntities();
	
	public void clearTable();
	
	public void clearTableSelection();
	
	public void addEntitiesToTable(List<Entity> entities);
		
	public void enableCreateChildButton(boolean enable);
	
	public void enableUpdateButton(boolean enable);
	
	public void enableDeleteButton(boolean enable);
	
	public void enableDeleteMultipleButton(boolean enable);
}
