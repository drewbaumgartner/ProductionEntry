package production.controller;

import java.io.File;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;

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
		System.exit(0);
	}
	
}
