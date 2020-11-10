package app.views;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import app.controllers.MainStageController;
import formatter.models.Entity;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainStage extends Stage implements MainStageListener {
	
	private MainStageController controller;

	private TableView<Entity> entityTableView = new TableView<Entity>();
	private List<TableColumn<Entity, ?>> cols = new ArrayList<TableColumn<Entity, ?>>();
	
	private Button createEntityButton = new Button("Create entity");
	private Button createChildEntityButton = new Button("Create child entity");
	private Button updateEntityButton = new Button("Update entity");
	private Button deleteEntityButton = new Button("Delete entity");
	private Button deleteEntitiesButton = new Button("Delete entities");
	
	private VBox sortBar = new VBox(30);
	
	private CheckBox sortAscDescCheckBox = new CheckBox("Sort ascending");
	
	private TextField idSearchField = new TextField();
	private TextField nameEqualsSearchField = new TextField();
	private TextField nameStartsWithSearchField = new TextField();
	private TextField nameEndsWithSearchField = new TextField();
	private TextField containAttributeKeySearchField = new TextField();
	private TextField containChildKeySearchField = new TextField();
	private TextField containChildKeyWithAttributeValueSearchFieldPartOne = new TextField();
	private TextField containChildKeyWithAttributeValueSearchFieldPartTwo = new TextField();
	
	public MainStage() {
		controller = new MainStageController(this);
		
		setTitle("Universal Data Formatter - UDF");
		
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
		
		sortAscDescCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
		        controller.onSortTypeChanged(entityTableView.getSortOrder().get(0));
		    }
		});
		
		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(entityTableView);
		
		HBox root = new HBox(30);
		
		VBox verticalBar = new VBox(30);
		verticalBar.setPadding(new Insets(20, 20, 20, 20));
		verticalBar.setAlignment(Pos.CENTER);
		verticalBar.getChildren().add(new Label("CRUD Operations"));
		verticalBar.getChildren().add(new Separator());
		verticalBar.getChildren().add(createEntityButton);
		verticalBar.getChildren().add(createChildEntityButton);
		verticalBar.getChildren().add(updateEntityButton);
		verticalBar.getChildren().add(deleteEntityButton);
		verticalBar.getChildren().add(deleteEntitiesButton);
		
		VBox sortBarHolder = new VBox(30);
		sortBarHolder.setPadding(new Insets(20, 20, 20, 0));
		sortBarHolder.setAlignment(Pos.CENTER);
		sortBarHolder.getChildren().add(new Label("Sort Operations"));
		sortBarHolder.getChildren().add(new Separator());
		sortBarHolder.getChildren().add(sortBar);
		sortBarHolder.getChildren().add(new Separator());
		sortBarHolder.getChildren().add(sortAscDescCheckBox);
		
		root.getChildren().add(verticalBar);
		Separator verticalSeparator = new Separator();
		verticalSeparator.setOrientation(Orientation.VERTICAL);
		root.getChildren().add(verticalSeparator);
		root.getChildren().add(sortBarHolder);
		
		BorderPane rootHolder = new BorderPane();
		rootHolder.setCenter(root);
		Separator horizontalSeparator = new Separator();
		horizontalSeparator.setOrientation(Orientation.HORIZONTAL);
		rootHolder.setBottom(horizontalSeparator);
		
		borderPane.setRight(rootHolder);
		
		HBox bottomBar = new HBox(30);
		bottomBar.setPadding(new Insets(20, 20, 20, 20));
		bottomBar.getChildren().add(new Label(controller.getInfoText()));
		borderPane.setBottom(bottomBar);
		
		TitledPane searchTitlePane = new TitledPane();
		searchTitlePane.setText("Search");
		searchTitlePane.setExpanded(false);
		VBox topBar = new VBox(5);
		HBox rowOne = new HBox(15);
		rowOne.setPadding(new Insets(10, 10, 10, 10));
		rowOne.getChildren().add(new Label("Id:"));
		idSearchField.setPromptText("Entity id");
		rowOne.getChildren().add(idSearchField);
		rowOne.getChildren().add(new Label("Name equals:"));
		nameEqualsSearchField.setPromptText("Entity name");
		rowOne.getChildren().add(nameEqualsSearchField);
		rowOne.getChildren().add(new Label("Name starts with:"));
		nameStartsWithSearchField.setPromptText("Entity name");
		rowOne.getChildren().add(nameStartsWithSearchField);
		rowOne.getChildren().add(new Label("Name ends with:"));
		nameEndsWithSearchField.setPromptText("Entity name");
		rowOne.getChildren().add(nameEndsWithSearchField);
		HBox rowTwo = new HBox(15);
		rowTwo.setPadding(new Insets(10, 10, 10, 10));
		rowTwo.getChildren().add(new Label("Contains attribute key:"));
		containAttributeKeySearchField.setPromptText("Attribute key");
		rowTwo.getChildren().add(containAttributeKeySearchField);
		rowTwo.getChildren().add(new Label("Contains child key:"));
		containChildKeySearchField.setPromptText("Child key");
		rowTwo.getChildren().add(containChildKeySearchField);
		rowTwo.getChildren().add(new Label("Contains child key with attribute value:"));
		containChildKeyWithAttributeValueSearchFieldPartOne.setPromptText("Child key");
		rowTwo.getChildren().add(containChildKeyWithAttributeValueSearchFieldPartOne);
		containChildKeyWithAttributeValueSearchFieldPartTwo.setPromptText("Child attribute value");
		rowTwo.getChildren().add(containChildKeyWithAttributeValueSearchFieldPartTwo);
		
		topBar.getChildren().add(rowOne);
		topBar.getChildren().add(rowTwo);
		searchTitlePane.setContent(topBar);
		borderPane.setTop(searchTitlePane);
		
		Scene scene = new Scene(borderPane);
		setMinWidth(1200);
		setMinHeight(800);
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
	
	public List<TableColumn<Entity, ?>> getTableColumns() {
		return entityTableView.getColumns();
	}
	
	public void setSortRadioButtons(List<RadioButton> radioButtons) {
		sortBar.getChildren().clear();
		sortBar.getChildren().addAll(radioButtons);
	}
	
	public void populateColumns(List<TableColumn<Entity, ?>> columns) {
		entityTableView.getColumns().clear();
		entityTableView.getColumns().addAll(columns);
		entityTableView.refresh();
	}
	
	public void sortTableByColumn(TableColumn<Entity, ?> column) {
		entityTableView.getSortOrder().clear();
		column.setSortType(sortAscDescCheckBox.isSelected() ? TableColumn.SortType.ASCENDING : TableColumn.SortType.DESCENDING);
		entityTableView.getSortOrder().add(column);
		entityTableView.getOnSort();
		entityTableView.sort();
	}
}
