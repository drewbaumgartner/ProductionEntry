package production.controller;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import production.MainApp;
import production.util.FileIO;

/**
 * The controller for the root layout.  The root layout provides the basic application layout containing a menu bar
 * and space where other JavaFX elements can be placed.
 * 
 * @author Drew
 *
 */
public class RootLayoutController {
	//Reference to the main application
	private MainApp mainApp;
	
	// Give access to the exitMenuItem in Scene Builder via FXML.
	@FXML
	private MenuItem exitMenuItem;
	
	
	/**
	 * Initializes an event handler for the exitMenuItem that fires a prompt alerting the user whenever the application is attempted to close
	 */
	public void initializeExitMenu()
	{
		mainApp.getPrimaryStage().setOnCloseRequest(confirmCloseEventHandler);
		exitMenuItem.setOnAction(event -> mainApp.getPrimaryStage().fireEvent(new WindowEvent(mainApp.getPrimaryStage(), WindowEvent.WINDOW_CLOSE_REQUEST)));
	}
	
	/**
	 * This method is called by the main application to give a reference back to itself.
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp)
	{
		this.mainApp = mainApp;
	}
	
	/**
	 * Creates an empty Production Report
	 */
	@FXML
	private void handleNew()
	{
		mainApp.getProductionEntryData().clear();
		mainApp.setProductionEntryFilePath(null);
	}
	
	/**
	 * Opens a FileChooser to let the user select a Production Report to load
	 */
	@FXML
	private void handleOpen()
	{
		FileChooser fileChooser = new FileChooser();
		// Set extension filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);
		
		// Show open file dialog
		File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
		
		if(file != null)
		{
			mainApp.loadProductionEntryDataFromFile(file);
		}
	}
	
	/**
	 * Saves the data to the ProductionEntry file that is currently open.  If the current data is not part of a file, then open the "Save As" dialog.
	 */
	@FXML
	private void handleSave()
	{
		File productionEntryFile = mainApp.getProductionEntryFilePath();
		
		if(productionEntryFile != null)
		{
			mainApp.saveProductionEntryDataToFile(productionEntryFile);
		}
		else
		{
			handleSaveAs();
		}
	}
	
	@FXML
	private void handleSaveAs()
	{
		FileChooser fileChooser = new FileChooser();
		
		// Set extension filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);

		// Show Save file dialog
		File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());
		
		if(file != null)
		{
			// If the file name does not end with ".xml" then add ".xml" to the end of the file name
			if(!file.getPath().endsWith(".xml"))
			{
				file = new File(file.getPath() + ".xml");
			}
			
			mainApp.saveProductionEntryDataToFile(file);
		}
	}
		
	/**
	 * Used to handle the event for the exitMenuItem
	 */
	private EventHandler<WindowEvent> confirmCloseEventHandler = event -> {
		Alert closeConfirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit? Any unsaved changes will be lost!");
		Button exitButton = (Button) closeConfirmation.getDialogPane().lookupButton(ButtonType.OK);
		exitButton.setText("Exit");
		closeConfirmation.setHeaderText("Confirm Exit");
		closeConfirmation.initModality(Modality.APPLICATION_MODAL);
		closeConfirmation.initOwner(mainApp.getPrimaryStage());
		
		Optional<ButtonType> closeResponse = closeConfirmation.showAndWait();
		if(!ButtonType.OK.equals(closeResponse.get()))
		{
			event.consume();
		}
	};
	
	@FXML
	private void updateVehicles()
	{
		
	}
	
	@FXML
	private void updateProducts()
	{
		
	}
	
	@FXML
	private void updateTechnicians()
	{	
		try
		{
			// Load the FXML file and create a new stage for the pop up dialog
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(mainApp.getClass().getResource("view/TextFileEditor.fxml"));
			AnchorPane dialog = (AnchorPane) loader.load();
			
			// Create the dialog stage
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Update Technicians");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(mainApp.getPrimaryStage());
			Scene scene = new Scene(dialog);
			dialogStage.setScene(scene);
			
			// Set the file type into the controller
			TextFileEditorController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setFile(FileIO.TECHNICIAN);
			controller.init();
			
			dialogStage.showAndWait();	
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
}
