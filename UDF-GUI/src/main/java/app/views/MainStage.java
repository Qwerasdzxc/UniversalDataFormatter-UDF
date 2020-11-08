package app.views;

import java.util.ArrayList;
import java.util.List;

import app.controllers.MainStageController;
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
	}

	public List<Entity> getTableEntities() {
		return entityTableView.getItems();
	}
}
