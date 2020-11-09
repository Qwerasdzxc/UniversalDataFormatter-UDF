package app.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.controllers.MainStageController;
import data_manipulation.finder.FinderProperties;
import data_manipulation.finder.NestedEntityAttributeFinder;
import formatter.DataFormatter;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import manager.UDFManager;
import models.Entity;

public class MainStage extends Stage implements MainStageView {
	
	private MainStageController controller;

	private TableView<Entity> entityTableView = new TableView<Entity>();
	private List<TableColumn<Entity, ?>> cols = new ArrayList<TableColumn<Entity, ?>>();
	
	public MainStage() {
		controller = new MainStageController(this);
		
		setupData();
		setTitle("Universal Data Formatter - UDF");
		
		controller.populateCommonColumns(cols);
		controller.populateCustomColumns(cols);
		
		entityTableView.getColumns().addAll(cols);
		entityTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
		Scene scene = new Scene(entityTableView, 800, 600);
		setScene(scene);
	}
	
	private void setupData() {
		DataFormatter formatter = UDFManager.getFormatter();
		entityTableView.getItems().clear();
		entityTableView.getItems().addAll(formatter.getAllEntities());
		
		Map<FinderProperties, Object> searchData = new HashMap<FinderProperties, Object>();
		searchData.put(FinderProperties.ID_EQUALS, 1);
		searchData.put(FinderProperties.NAME_STARTS_WITH, "Pr");
		System.out.println(formatter.searchForEntities(searchData));
	}

	public List<Entity> getTableEntities() {
		return entityTableView.getItems();
	}
}
