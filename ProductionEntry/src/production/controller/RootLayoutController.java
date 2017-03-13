package production.controller;

import java.io.File;
import java.util.Optional;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.WindowEvent;
import production.MainApp;

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
	 * Closes the application
	 */
	@FXML
	private void handleExit()
	{
		System.out.println("Exit menu selected");
		
		//System.exit(0);
	}
	
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
	
}