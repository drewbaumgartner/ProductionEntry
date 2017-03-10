package production;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import production.controller.ProductionReportController;
import production.controller.ProductionReportEditDialogController;
import production.controller.RootLayoutController;
import production.model.ProductionEntry;
import production.model.ProductionEntryListWrapper;

public class MainApp extends Application{

	private Stage primaryStage;
	private BorderPane rootLayout;
	
	private ObservableList<ProductionEntry> productionEntryData = FXCollections.observableArrayList();
	
	/**
	 * Constructor
	 */
	public MainApp()
	{
		productionEntryData.add(new ProductionEntry());
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Daily Production Entry");
		
		initRootLayout();
		showProductionReport();
	}
	
	
	/**
	 * Initializes the root layout
	 */
	public void initRootLayout()
	{
		try
		{
			// Load root layout from fxml file
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			
			// Show the scene containing the root layout
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			
			// Give the controller access to the main app
			RootLayoutController controller = loader.getController();
			controller.setMainApp(this);
			
			// Display the primary stage
			primaryStage.show();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		// Load the last opened ProductionEntry file
		File file = getProductionEntryFilePath();
		if(file != null)
		{
			loadProductionEntryDataFromFile(file);
		}
	}
	
	/**
	 * Shows the Production Entry inside the root layout
	 */
	public void showProductionReport()
	{
		try
		{
			// Load person overview
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/ProductionReport.fxml"));
			AnchorPane productionEntryView = (AnchorPane) loader.load();
			
			// Set person overview into the center of root layout
			rootLayout.setCenter(productionEntryView);
						
			// Give the controller access to the MainApp
			ProductionReportController controller = loader.getController();
			controller.setMainApp(this);
			
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Opens a dialog window to edit the details for the specified production entry.
	 * @param entry
	 * @return
	 */
	public boolean showProductionReportEditDialog(ProductionEntry entry, String title)
	{
		try
		{
			// Load the FXML file and create a new stage for the pop-up dialog
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/ProductionReportEditDialog.fxml"));
			AnchorPane dialog = (AnchorPane) loader.load();
			
			// Create the dialog stage
			Stage dialogStage = new Stage();
			dialogStage.setTitle(title);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(dialog);
			dialogStage.setScene(scene);
			
			// Set the production entry into the controller
			ProductionReportEditDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setProductionEntry(entry);
			
			// Show the dialog window and wait until the user selects OK, Cancel, or closes it
			dialogStage.showAndWait();
			
			return controller.isOkClicked();
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public ObservableList<ProductionEntry> getProductionEntryData()
	{
		return productionEntryData;
	}
	
	/**
	 * Returns the main stage
	 * @return
	 */
	public Stage getPrimaryStage()
	{
		return primaryStage;
	}
	
	/**
	 * Returns the Production Entry file preference (the file that was last opened).
	 * @return
	 */
	public File getProductionEntryFilePath()
	{
		Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
		String filePath = prefs.get("filePath", null);
		
		if(filePath != null)
		{
			return new File(filePath);
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Sets the file path of the currently loaded file.
	 * @param file
	 */
	public void setProductionEntryFilePath(File file)
	{
		Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
		
		if(file != null)
		{
			prefs.put("filePath", file.getPath());
			
			// Update the stage title
			primaryStage.setTitle("Production Entry - " + file.getName());
		}
		else
		{
			prefs.remove("filePath");
			
			// Update the stage title
			primaryStage.setTitle("Production Entry");
		}
	}
	
	/**
	 * Loads the ProductionEntry data from the specified file.  The current ProductionEntry will be replaced.
	 * @param file
	 */
	public void loadProductionEntryDataFromFile(File file)
	{
		try
		{
			JAXBContext context = JAXBContext.newInstance(ProductionEntryListWrapper.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			
			// Reading XML from the file and unmarshalling.
			ProductionEntryListWrapper wrapper = (ProductionEntryListWrapper) unmarshaller.unmarshal(file);
			
			productionEntryData.clear();
			productionEntryData.addAll(wrapper.getProductionEntries());
			
			// Save the file path to the registry.
			setProductionEntryFilePath(file);
		}
		catch(Exception e)
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Could not load data");
			alert.setContentText("Could not load data from file:\n" + file.getPath());
			alert.showAndWait();
		}
	}
	
	/**
	 * Saves the current ProductionEntry data to the specified file.
	 * @param file
	 */
	public void saveProductionEntryDataToFile(File file)
	{
		try
		{
			JAXBContext context = JAXBContext.newInstance(ProductionEntryListWrapper.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			
			// Wrapping the ProductionEntry object
			ProductionEntryListWrapper wrapper = new ProductionEntryListWrapper();
			wrapper.setProductionEntries(productionEntryData);
			
			// Marshalling and saving XML to the file.
			marshaller.marshal(wrapper, file);
			
			// Save the file path to the registry.
			setProductionEntryFilePath(file);
		}
		catch(Exception e)
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Could not save data");
			alert.setContentText("Could not save data to file:\n" + file.getPath());
			alert.showAndWait();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
