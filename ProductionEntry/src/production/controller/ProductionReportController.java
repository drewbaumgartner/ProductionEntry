package production.controller;

import java.time.LocalDate;

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
		dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
		techColumn.setCellValueFactory(cellData -> cellData.getValue().technicianProperty());
		
		entryTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showEntryDetails(newValue));
	}
	
	public void showEntryDetails(ProductionEntry entry)
	{
		// If the ProductionEntry is not null, then populate all the labels with text
		if(entry != null)
		{
			dateLabel.setText(DateUtil.format(entry.getDate()));
			technicianLabel.setText(entry.getTechnician());
			hoursWorkedLabel.setText(Double.toString(entry.getHoursWorked()));
			vehicleLabel.setText(entry.getVehicle());
			milesOutLabel.setText(Integer.toString(entry.getMilesOut()));
			milesInLabel.setText(Integer.toString(entry.getMilesIn()));
			totalProductionLabel.setText(Double.toString(entry.getTotalProduction()));
			productUsedLabel.setText(entry.getProductUsed());
			productionTypeLabel.setText(entry.getProductionType());
			materialStartLabel.setText(Double.toString(entry.getMaterialStart()));
			materialEndLabel.setText(Double.toString(entry.getMaterialEnd()));
			sqFtTreatedLabel.setText(Double.toString(entry.getSquareFeetTreated()));
			rateLabel.setText(Double.toString(entry.getRate()));
			materialNeededLabel.setText(Double.toString(entry.getMaterialNeeded()));
			materialUsedLabel.setText(Double.toString(entry.getMaterialUsed()));
			materialVarianceLabel.setText(Double.toString(entry.getMaterialVariance()));
			milesDrivenLabel.setText(Integer.toString(entry.getMilesDriven()));
			productionPerHourLabel.setText(Double.toString(entry.getProductionPerHour()));
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
	
	@FXML
	private void handleNewPerson()
	{
		
	}
	
	@FXML
	private void handleEditEntry()
	{
		ProductionEntry selectedEntry = entryTable.getSelectionModel().getSelectedItem();
		
		if(selectedEntry != null)
		{
			boolean okClicked = mainApp.showProductionReportEditDialog(selectedEntry);
			
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
}
