package production.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class TextFileEditorController {

	@FXML
	private ListView<String> listView;
	@FXML
	private TextField textField;
	
	private String whichFile;
	
	public TextFileEditorController(String whichFile)
	{
		this.whichFile = whichFile;
	}
	
	@FXML
	private void initialize()
	{
		if(whichFile == "Technician")
		{
			readTechnicianFile();
		}
		else if(whichFile == "Vehicle")
		{
			readVehicleFile();
		}
		else if(whichFile == "Product")
		{
			readProductFile();
		}
		
	}
	
	private void readTechnicianFile()
	{
		
	}
	
	private void readVehicleFile()
	{
		
	}
	
	private void readProductFile()
	{
		
	}
	
}
