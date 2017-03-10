package production;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import production.controller.ProductionReportController;
import production.controller.ProductionReportEditDialogController;
import production.model.ProductionEntry;

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
			primaryStage.show();
		}
		catch(IOException e)
		{
			e.printStackTrace();
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
	public boolean showProductionReportEditDialog(ProductionEntry entry)
	{
		try
		{
			// Load the FXML file and create a new stage for the pop-up dialog
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/ProductionReportEditDialog.fxml"));
			AnchorPane dialog = (AnchorPane) loader.load();
			
			// Create the dialog stage
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Production Entry");
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
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
