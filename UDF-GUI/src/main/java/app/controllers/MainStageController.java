package app.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import app.views.MainStageView;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import models.Entity;

public class MainStageController {
	
	private MainStageView view;
	
	public MainStageController(MainStageView view) {
		this.view = view;
	}

	public void populateCommonColumns(List<TableColumn<Entity, ?>> columns) {		
		TableColumn<Entity, Integer> idCol = new TableColumn<Entity, Integer>("Id");
		idCol.setCellValueFactory(new PropertyValueFactory<Entity, Integer>("id"));
		
		TableColumn<Entity, String> nameCol = new TableColumn<Entity, String>("Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<Entity, String>("name"));
		
		columns.add(idCol);
		columns.add(nameCol);
	}
	
	public void populateCustomColumns(List<TableColumn<Entity, ?>> columns) {		
		List<String> attributeColumnNames = new ArrayList<String>();
		List<String> childrenColumnNames = new ArrayList<String>();
		
		for (Entity entity : view.getTableEntities()) {
			
			if (entity.getAttributes() != null) {
				for (Map.Entry<String, Object> entry : entity.getAttributes().entrySet()) {
					final String key = entry.getKey();
					if (!attributeColumnNames.contains(key)) {
						TableColumn<Entity, Object> attributeCol = new TableColumn<Entity, Object>("Attribute - " + key);
						attributeCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Entity, Object>,ObservableValue<Object>>(){
				            public ObservableValue<Object> call(TableColumn.CellDataFeatures<Entity, Object> param) {
				            	Map<String, Object> attributes = param.getValue().getAttributes();
				            	if (attributes == null || attributes.get(key) == null)
				            		return null;
				            	
				            	Object attribute = attributes.get(key);
				                return new SimpleObjectProperty<Object>(attribute);
				            }
				        });
						
						columns.add(attributeCol);
						attributeColumnNames.add(key);
					}
				}
			}
			
			if (entity.getChildren() != null) {
				for (Map.Entry<String, Entity> entry : entity.getChildren().entrySet()) {
					final String key = entry.getKey();
					if (!childrenColumnNames.contains(key)) {
						TableColumn<Entity, String> childCol = new TableColumn<Entity, String>("Child - " + key);
						childCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Entity, String>,ObservableValue<String>>(){
				            public ObservableValue<String> call(TableColumn.CellDataFeatures<Entity, String> param) {
				            	Map<String, Entity> children = param.getValue().getChildren();
				            	if (children == null || children.get(key) == null)
				            		return null;
				            	
				            	Entity child = children.get(key);
			            		return new SimpleStringProperty(child.getName() + " (Id " + child.getId() + ")");
				            }
				        });
						
						columns.add(childCol);
						childrenColumnNames.add(key);
					}
				}
			}
		}
	}
}
