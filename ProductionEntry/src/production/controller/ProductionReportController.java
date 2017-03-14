package production.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import production.MainApp;
import production.model.ProductionEntry;
import production.util.DateUtil;

public class ProductionReportController {
	@FXML
	private TableView<ProductionEntry> entryTable;
	@FXML
	private TableColumn<ProductionEntry, LocalDate> dateColumn;
	@FXML
	private TableColumn<ProductionEntry, String> techColumn;
	@FXML
	private TableColumn<ProductionEntry, Number> producedColumn;
	@FXML
	private Label dateLabel;
	@FXML
	private Label technicianLabel;
	@FXML
	private Label hoursWorkedLabel;
	@FXML
	private Label vehicleLabel;
	@FXML
	private Label milesOutLabel;
	@FXML
	private Label milesInLabel;
	@FXML
	private Label totalProductionLabel;
	@FXML
	private Label productUsedLabel;
	@FXML
	private Label productionTypeLabel;
	@FXML
	private Label materialStartLabel;
	@FXML
	private Label materialEndLabel;
	@FXML
	private Label sqFtTreatedLabel;
	@FXML
	private Label rateLabel;
	@FXML
	private Label materialNeededLabel;
	@FXML
	private Label materialUsedLabel;
	@FXML
	private Label materialVarianceLabel;
	@FXML
	private Label milesDrivenLabel;
	@FXML
	private Label productionPerHourLabel;
	
	// Reference to the main application
	private MainApp mainApp;
	
	/**
	 * Empty constructor.  This constructor is called before the initialize() method.
	 */
	public ProductionReportController()
	{
		
	}
	
	@FXML
	private void initialize()
	{
		// Resource used: http://stackoverflow.com/questions/38045546/formatting-an-objectpropertylocaldatetime-in-a-tableview-column
		// This block of code formats the dateColumn in the TableView to use the specified Date format located in the DateUtil class
		dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
		dateColumn.setCellFactory(col -> new TableCell<ProductionEntry, LocalDate>(){
			@Override
			protected void updateItem(LocalDate item, boolean empty){
				super.updateItem(item, empty);
				if(empty) setText(null);
				else setText(String.format(item.format(DateUtil.DATE_FORMATTER)));
			}
		});
		
		techColumn.setCellValueFactory(cellData -> cellData.getValue().technicianProperty());
		
		// Resource used: http://stackoverflow.com/questions/2379221/java-currency-number-format
		// This block of code is similar to the above block for the Date Column
		// It will format the number in the producedColumn as a US currency (gives it a $ sign and formats to 2 decimal places).
		producedColumn.setCellValueFactory(cellData -> cellData.getValue().totalProductionProperty());
		producedColumn.setCellFactory(col -> new TableCell<ProductionEntry, Number>(){
			@Override
			protected void updateItem(Number item, boolean empty){
				super.updateItem(item, empty);
				if(empty) setText(null);
				else setText(NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(item));
			}
		});
		
		entryTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showEntryDetails(newValue));
	}
	
	public void showEntryDetails(ProductionEntry entry)
	{
		// If the ProductionEntry is not null, then populate all the labels with their respective variable values
		if(entry != null)
		{
			DecimalFormat df = new DecimalFormat("#,###.00");
			
			dateLabel.setText(DateUtil.format(entry.getDate()));
			technicianLabel.setText(entry.getTechnician());
			hoursWorkedLabel.setText(df.format(entry.getHoursWorked()));
			vehicleLabel.setText(entry.getVehicle());
			milesOutLabel.setText(Integer.toString(entry.getMilesOut()));
			milesInLabel.setText(Integer.toString(entry.getMilesIn()));
			totalProductionLabel.setText(DecimalFormat.getCurrencyInstance(new Locale("en", "US")).format(entry.getTotalProduction()));
			productUsedLabel.setText(entry.getProductUsed());
			productionTypeLabel.setText(entry.getProductionType());
			materialStartLabel.setText(df.format(entry.getMaterialStart()));
			materialEndLabel.setText(df.format(entry.getMaterialEnd()));
			sqFtTreatedLabel.setText(df.format(entry.getSquareFeetTreated()));
			rateLabel.setText(df.format(entry.getRate()));

			// Display the 5 calculated fields
			entry.calculateMaterialNeeded();
			materialNeededLabel.setText(df.format(entry.getMaterialNeeded()));
			entry.calculateMaterialUsed();
			materialUsedLabel.setText(df.format(entry.getMaterialUsed()));
			entry.calculateMaterialVariance();
			materialVarianceLabel.setText(DecimalFormat.getPercentInstance().format(entry.getMaterialVariance()));
			entry.calculateMilesDriven();
			milesDrivenLabel.setText(Integer.toString(entry.getMilesDriven()));
			entry.calculateProductionPerHour();
			productionPerHourLabel.setText(DecimalFormat.getCurrencyInstance(new Locale("en", "US")).format(entry.getTotalProduction()));
		}
		// Else the ProductionEntry is null, remove all text from each label
		else
		{
			dateLabel.setText("");
			technicianLabel.setText("");
			hoursWorkedLabel.setText("");
			vehicleLabel.setText("");
			milesOutLabel.setText("");
			milesInLabel.setText("");
			totalProductionLabel.setText("");
			productUsedLabel.setText("");
			productionTypeLabel.setText("");
			materialStartLabel.setText("");
			materialEndLabel.setText("");
			sqFtTreatedLabel.setText("");
			rateLabel.setText("");
			materialNeededLabel.setText("");
			materialUsedLabel.setText("");
			materialVarianceLabel.setText("");
			milesDrivenLabel.setText("");
			productionPerHourLabel.setText("");
		}
	}
	
	/**
	 * Method is called by the main application to give a reference back to itself.
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp)
	{
		this.mainApp = mainApp;
		
		// Add observable list data to the data
		entryTable.setItems(mainApp.getProductionEntryData());
	}
	
	/**
	 * Called when the user clicks the new button.  Opens a dialog to edit details for a new ProductionEntry.
	 */
	@FXML
	private void handleNewProductionEntry()
	{
		ProductionEntry tempEntry = new ProductionEntry();
		
		boolean okClicked = mainApp.showProductionReportEditDialog(tempEntry, "New Production Entry");
		
		if(okClicked)
		{
			mainApp.getProductionEntryData().add(tempEntry);
		}
	}
	
	/**
	 * Called when the user clicks the edit button.  Opens a dialog to edit details for the currently selected ProductionEntry. 
	 */
	@FXML
	private void handleEditEntry()
	{
		ProductionEntry selectedEntry = entryTable.getSelectionModel().getSelectedItem();
		
		if(selectedEntry != null)
		{
			boolean okClicked = mainApp.showProductionReportEditDialog(selectedEntry, "Edit Production Entry");
			
			if(okClicked)
			{
				showEntryDetails(selectedEntry);
			}
		}
		else
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Entry Selected!");
			alert.setContentText("Please select an entry in the table.");
		}
	}
	
	/**
	 * Called when the user clicks on the delete button
	 */
	@FXML
	private void handleDeleteEntry()
	{
		int selectedIndex = entryTable.getSelectionModel().getSelectedIndex();
		
		if(selectedIndex >= 0)
		{
			entryTable.getItems().remove(selectedIndex);
		}
		else
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No production entry is selected!");
			alert.setContentText("Please select a production entry in the table.");
			alert.showAndWait();
		}
	}
}
