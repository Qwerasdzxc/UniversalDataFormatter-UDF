package app.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import app.views.MainStageListener;
import formatter.DataFormatter;
import formatter.manager.UDFManager;
import formatter.models.Entity;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class MainStageController {
	
	private DataFormatter formatter;
	private MainStageListener view;
	
	public MainStageController(MainStageListener view) {
		this.view = view;
		this.formatter = UDFManager.getFormatter();
		
		refresh();
	}
	
	public void onCreateEntityClicked() {
		
	}
	
	public void onCreateChildEntityClicked() {
		
	}
	
	public void onUpdateEntityClicked() {
		
	}
	
	public void deleteEntity(Entity entity, boolean cascade) {
		formatter.deleteEntity(entity, cascade);
		refresh();
	}
	
	public void onDeleteEntitiesClicked() {
		
	}
	
	
	public void onTableSelectionChanged() {
		if (view.getSelectedEntities().size() > 1)
			enableButtonsForMultipleSelection();
		else
			enableButtonsForSingleSelection();
	}

	public List<TableColumn<Entity, ?>> populateCommonColumns() {
		List<TableColumn<Entity, ?>> columns = new ArrayList<TableColumn<Entity,?>>();
		TableColumn<Entity, Integer> idCol = new TableColumn<Entity, Integer>("Id");
		idCol.setCellValueFactory(new PropertyValueFactory<Entity, Integer>("id"));
		
		TableColumn<Entity, String> nameCol = new TableColumn<Entity, String>("Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<Entity, String>("name"));
		
		columns.add(idCol);
		columns.add(nameCol);
		
		return columns;
	}
	
	public List<TableColumn<Entity, ?>> populateCustomColumns() {
		List<TableColumn<Entity, ?>> columns = new ArrayList<TableColumn<Entity,?>>();
		
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
		return columns;
	}
	
	public List<RadioButton> getSortRadioButtons() {
		ToggleGroup group = new ToggleGroup();
		
		List<RadioButton> buttons = new ArrayList<RadioButton>();
		List<TableColumn<Entity, ?>> columns = view.getTableColumns();
		
		for (final TableColumn<Entity, ?> column : columns) {
			RadioButton button = new RadioButton();
			button.setText(column.getText());
			button.setUserData(column.getId());
			button.setToggleGroup(group);
			button.selectedProperty().addListener(new ChangeListener<Boolean>() {
			    public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
			        if (isNowSelected) {
			        	onSortTypeChanged(column);
			        }
			    }
			});
			
			if (button.getText().equals("Id"))
				button.setSelected(true);
			
			buttons.add(button);
		}
		
		return buttons;
	}
	
	public void onSortTypeChanged(TableColumn<Entity, ?> column) {
		view.sortTableByColumn(column);
	}

	public String getInfoText() {
		return formatter.getInfoText();
	}
	
	private void refresh() {
		view.clearTable();
		view.clearTableSelection();
		
		List<Entity> data = formatter.getAllEntities();
		List<Entity> children = new ArrayList<Entity>();

		for (Entity entity : data) {
			if (entity.getChildren() == null)
				continue;

			children.addAll(entity.getChildren().values());
		}
		data.addAll(children);
		
		view.addEntitiesToTable(data);
		
		List<TableColumn<Entity, ?>> columns = new ArrayList<TableColumn<Entity,?>>();
		columns.addAll(populateCommonColumns());
		columns.addAll(populateCustomColumns());
		
		view.populateColumns(columns);
		
		enableButtonsForNoSelection();
		view.setSortRadioButtons(getSortRadioButtons());
	}
	
	private void enableButtonsForNoSelection() {
		view.enableCreateChildButton(false);
		view.enableUpdateButton(false);
		view.enableDeleteButton(false);
		view.enableDeleteMultipleButton(false);
	}
	
	private void enableButtonsForSingleSelection() {
		view.enableCreateChildButton(true);
		view.enableUpdateButton(true);
		view.enableDeleteButton(true);
		view.enableDeleteMultipleButton(false);
	}
	
	private void enableButtonsForMultipleSelection() {
		view.enableCreateChildButton(false);
		view.enableUpdateButton(false);
		view.enableDeleteButton(false);
		view.enableDeleteMultipleButton(true);
	}
}
