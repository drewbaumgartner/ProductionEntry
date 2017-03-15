package production.controller;

import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import production.model.ProductionEntry;
import production.util.DateUtil;
import production.util.FileIO;

public class ProductionReportEditDialogController {
	
	@FXML
	private TextField dateField;
	@FXML
	private TextField hoursWorkedField;
	@FXML
	private TextField milesOutField;
	@FXML
	private TextField milesInField;
	@FXML
	private TextField totalProductionField;
	@FXML
	private TextField materialStartField;
	@FXML
	private TextField materialEndField;
	@FXML
	private TextField sqFtTreatedField;
	@FXML
	private TextField rateField;
	@FXML
	private ChoiceBox<String> vehicleChoiceBox;
	@FXML
	private ComboBox<String> technicianComboBox;
	@FXML
	private ChoiceBox<String> productionTypeChoiceBox;
	@FXML
	private ComboBox<String> productUsedComboBox;
	
	private Stage dialogStage;
	private ProductionEntry entry;
	private boolean okClicked = false;
		
	private FileIO fileIO;
	
	ObservableList<String> productionTypeList = FXCollections.observableArrayList("Dry", "Liquid");
	ObservableList<String> vehicleList = FXCollections.observableArrayList();
	ObservableList<String> technicianList = FXCollections.observableArrayList();
	ObservableList<String> productUsedList = FXCollections.observableArrayList();
			
	@FXML
	private void initialize()
	{			
		// Read the technician, vehicle, and productUsed files and add their data to their respective Observable List
		fileIO = new FileIO();
		technicianList.addAll(fileIO.readFile(FileIO.TECHNICIANS_FILE));
		vehicleList.addAll(fileIO.readFile(FileIO.VEHICLES_FILE));
		productUsedList.addAll(fileIO.readFile(FileIO.PRODUCTS_FILE));	
		
		// Sort lists before assigning them to combo/choice boxes
		Collections.sort(vehicleList);
		Collections.sort(technicianList);
		Collections.sort(productUsedList);
		
		// Assign the lists to their GUI components
		productionTypeChoiceBox.setItems(productionTypeList);
		vehicleChoiceBox.setItems(vehicleList);
		technicianComboBox.setItems(technicianList);
		productUsedComboBox.setItems(productUsedList);
	}
	
	public void setDialogStage(Stage dialogStage)
	{
		this.dialogStage = dialogStage;
	}
	
	/**
	 * Sets the selected production entry to be loaded into the edit dialog window
	 * @param entry
	 */
	public void setProductionEntry(ProductionEntry entry)
	{
		this.entry = entry;
		
		dateField.setText(DateUtil.format(entry.getDate()));
		dateField.setPromptText("mm-dd-yyyy");
		technicianComboBox.setValue(entry.getTechnician());
		hoursWorkedField.setText(Double.toString(entry.getHoursWorked()));
		vehicleChoiceBox.setValue(entry.getVehicle());
		milesOutField.setText(Integer.toString(entry.getMilesOut()));
		milesInField.setText(Integer.toString(entry.getMilesIn()));
		totalProductionField.setText(Double.toString(entry.getTotalProduction()));
		productUsedComboBox.setValue(entry.getProductUsed());
		productionTypeChoiceBox.setValue(entry.getProductionType());
		materialStartField.setText(Double.toString(entry.getMaterialStart()));
		materialEndField.setText(Double.toString(entry.getMaterialEnd()));
		sqFtTreatedField.setText(Double.toString(entry.getSquareFeetTreated()));
		rateField.setText(Double.toString(entry.getRate()));
	}
	
	/**
	 * @return true if the user clicked OK, otherwise return false
	 */
	public boolean isOkClicked()
	{
		return okClicked;
	}
	
	/**
	 * Called when the user clicks OK.
	 */
	@FXML
	private void handleOk()
	{
		// If the input is valid then set the entry's information equal to all the text fields
		if(isInputValid())
		{
			entry.setDate(DateUtil.parse(dateField.getText()));
			entry.setTechnician(technicianComboBox.getValue());
			entry.setHoursWorked(Double.parseDouble(hoursWorkedField.getText()));
			entry.setVehicle(vehicleChoiceBox.getValue());
			entry.setMilesOut(Integer.parseInt(milesOutField.getText()));
			entry.setMilesIn(Integer.parseInt(milesInField.getText()));
			entry.setTotalProduction(Double.parseDouble(totalProductionField.getText()));
			entry.setProductUsed(productUsedComboBox.getValue());
			entry.setProductionType(productionTypeChoiceBox.getValue());
			entry.setMaterialStart(Double.parseDouble(materialStartField.getText()));
			entry.setMaterialEnd(Double.parseDouble(materialEndField.getText()));
			entry.setSquareFeetTreated(Double.parseDouble(sqFtTreatedField.getText()));
			entry.setRate(Double.parseDouble(rateField.getText()));
			entry.calculateMaterialNeeded();
			entry.calculateMaterialUsed();
			entry.calculateMaterialVariance();
			entry.calculateMilesDriven();
			entry.calculateProductionPerHour();
			
			okClicked = true;
			dialogStage.close();
		}
	}
	
	/**
	 * Called when the user clicks cancel.
	 */
	@FXML
	private void handleCancel()
	{
		dialogStage.close();
	}
	
	/**
	 * Validates the user input in the text fields.
	 * @return true if input is valid
	 */
	private boolean isInputValid()
	{
		String errorMessage = "";
		
		if(dateField.getText() == null || dateField.getText().length() == 0)
		{
			errorMessage += "Date is empty!\n";
		}
		else
		{
			if(!DateUtil.validDate(dateField.getText()))
			{
				errorMessage += "Invalid date!  Use the format mm-dd-yyyy!\n";
			}
		}
		
		if(technicianComboBox.getSelectionModel().isEmpty())
		{
			errorMessage += "Technician is empty!\n";
		}
		
		if(hoursWorkedField.getText() == null || hoursWorkedField.getText().length() == 0)
		{
			errorMessage += "Hours worked is empty!\n";
		}
		else
		{
			try
			{
				Double.parseDouble(hoursWorkedField.getText());
			}
			catch(NumberFormatException e)
			{
				errorMessage += "Invalid input for hours worked!  Must be a valid number!\n";
			}
		}
		
		if(vehicleChoiceBox.getSelectionModel().isEmpty())
		{
			errorMessage += "Vehicle is empty!\n";
		}
		
		if(milesOutField.getText() == null || milesOutField.getText().length() == 0)
		{
			errorMessage += "Miles out is empty!\n";
		}
		else
		{
			try
			{
				Double.parseDouble(milesOutField.getText());
			}
			catch(NumberFormatException e)
			{
				errorMessage += "Invalid input for miles out! Must be a valid number!\n";
			}
		}
		
		if(milesInField.getText() == null || milesInField.getText().length() == 0)
		{
			errorMessage += "Miles in is empty!\n";
		}
		else
		{
			try
			{
				Double.parseDouble(milesInField.getText());
			}
			catch(NumberFormatException e)
			{
				errorMessage += "Invalid input for miles in! Must be a valid number!\n";
			}
		}
		
		if(totalProductionField.getText() == null || totalProductionField.getText().length() == 0)
		{
			errorMessage += "Total production is empty!\n";
		}
		else
		{
			try
			{
				Double.parseDouble(totalProductionField.getText());
			}
			catch(NumberFormatException e)
			{
				errorMessage += "Invalid input for total production! Must be a valid number!\n";
			}
		}
		
		if(productUsedComboBox.getSelectionModel().isEmpty())
		{
			errorMessage += "Product used is empty!\n";
		}
		
		if(productionTypeChoiceBox.getSelectionModel().isEmpty())
		{
			errorMessage += "Production type is empty!\n";
		}
		
		if(materialStartField.getText() == null || materialStartField.getText().length() == 0)
		{
			errorMessage += "Material start is empty!\n";
		}
		else
		{
			try
			{
				Double.parseDouble(materialStartField.getText());
			}
			catch(NumberFormatException e)
			{
				errorMessage += "Invalid material start! Must be a valid number!\n";
			}
		}
		
		if(materialEndField.getText() == null || materialEndField.getText().length() == 0)
		{
			errorMessage += "Material end is empty!\n";
		}
		else
		{
			try
			{
				Double.parseDouble(materialEndField.getText());
			}
			catch(NumberFormatException e)
			{
				errorMessage += "Invalid material end! Must be a valid number!\n";
			}
		}
		
		if(sqFtTreatedField.getText() == null || sqFtTreatedField.getText().length() == 0)
		{
			errorMessage += "Square foot treated is empty!\n";
		}
		else
		{
			try
			{
				Double.parseDouble(sqFtTreatedField.getText());
			}
			catch(NumberFormatException e)
			{
				errorMessage += "Invalid square foot treated! Must be a valid number!\n";
			}
		}
		
		if(rateField.getText() == null || rateField.getText().length() == 0)
		{
			errorMessage += "Rate is empty!\n";
		}
		else
		{
			try
			{
				Double.parseDouble(rateField.getText());
			}
			catch(NumberFormatException e)
			{
				errorMessage += "Invalid rate! Must be a valid number!\n";
			}
		}
		
		if(errorMessage.length() == 0)
		{
			return true;
		}
		else
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields!");
			alert.setContentText(errorMessage);
			alert.showAndWait();
			return false;
		}
	}
}
