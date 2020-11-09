package app.views;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import app.controllers.MainStageController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Entity;

public class MainStage extends Stage implements MainStageListener {
	
	private MainStageController controller;

	private TableView<Entity> entityTableView = new TableView<Entity>();
	private List<TableColumn<Entity, ?>> cols = new ArrayList<TableColumn<Entity, ?>>();
	
	private Button createEntityButton = new Button("Create entity");
	private Button createChildEntityButton = new Button("Create child entity");
	private Button updateEntityButton = new Button("Update entity");
	private Button deleteEntityButton = new Button("Delete entity");
	private Button deleteEntitiesButton = new Button("Delete entities");
	
	public MainStage() {
		controller = new MainStageController(this);
		
		setTitle("Universal Data Formatter - UDF");
		
		controller.populateCommonColumns(cols);
		controller.populateCustomColumns(cols);
		
		entityTableView.getColumns().addAll(cols);
		entityTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		entityTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Entity>() {
			public void changed(ObservableValue<? extends Entity> observable, Entity oldValue, Entity newValue) {
				controller.onTableSelectionChanged();
			}
		});
		
		deleteEntityButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Entity selected = getSelectedEntities().get(0);
				
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirm");
				alert.setHeaderText("Delete selected Entity?");
				
				CheckBox cascadeDeleteCheckBox = new CheckBox("Cascade delete children?");
				cascadeDeleteCheckBox.setDisable(selected.getChildren() == null);
				alert.getDialogPane().setContent(cascadeDeleteCheckBox);
				 
				Optional<ButtonType> result = alert.showAndWait();
				 
				if ((result.isPresent()) && (result.get() == ButtonType.OK))
					controller.deleteEntity(selected, cascadeDeleteCheckBox.isSelected());
			}
		});;
		
		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(entityTableView);
		
		VBox verticalBar = new VBox(30);
		verticalBar.setPadding(new Insets(20, 20, 20, 20));
		verticalBar.setAlignment(Pos.CENTER);
		verticalBar.getChildren().add(createEntityButton);
		verticalBar.getChildren().add(createChildEntityButton);
		verticalBar.getChildren().add(updateEntityButton);
		verticalBar.getChildren().add(deleteEntityButton);
		verticalBar.getChildren().add(deleteEntitiesButton);
		borderPane.setRight(verticalBar);
		
		HBox bottomBar = new HBox(30);
		bottomBar.setPadding(new Insets(20, 20, 20, 20));
		bottomBar.getChildren().add(new Label(controller.getInfoText()));
		borderPane.setBottom(bottomBar);
		
		Scene scene = new Scene(borderPane, 800, 600);
		setScene(scene);
	}
	
	public List<Entity> getTableEntities() {
		return entityTableView.getItems();
	}

	public void clearTable() {
		entityTableView.getItems().clear();
	}
	
	public void clearTableSelection() {
		entityTableView.getSelectionModel().clearSelection();
	}

	public void addEntitiesToTable(List<Entity> entities) {
		entityTableView.getItems().addAll(entities);
	}

	public void enableCreateChildButton(boolean enable) {
		createChildEntityButton.setDisable(!enable);
	}

	public void enableUpdateButton(boolean enable) {
		updateEntityButton.setDisable(!enable);
	}

	public void enableDeleteButton(boolean enable) {
		deleteEntityButton.setDisable(!enable);
	}

	public void enableDeleteMultipleButton(boolean enable) {
		deleteEntitiesButton.setDisable(!enable);
	}

	public List<Entity> getSelectedEntities() {
		return entityTableView.getSelectionModel().getSelectedItems();
	}
}
