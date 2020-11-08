package app.views;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import app.controllers.MainStageController;
import formatter.DataFormatter;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
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
		
		try {
			entityTableView.getItems().addAll(formatter.read());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Entity> getTableEntities() {
		return entityTableView.getItems();
	}
}