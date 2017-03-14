package production.controller;

import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import production.util.FileIO;

public class TextFileEditorController {

	@FXML
	private ListView<String> listView;
	@FXML
	private TextField textField;
	@FXML
	private Label label;
	
	private String whichFile;
	private FileIO fileIO;
	private Stage dialogStage;
	
	
	ObservableList<String> list = FXCollections.observableArrayList();
	//ObservableSet<String> list = FXCollections.observableSet();
	
	public TextFileEditorController()
	{
		
	}
	
	@FXML
	private void initialize()
	{

	}
	
	public void init()
	{
		fileIO = new FileIO();
		
		if(whichFile.equals(FileIO.TECHNICIAN))
		{
			list.addAll(fileIO.readFile(FileIO.TECHNICIAN));
			label.setText("Enter Technician:");
		}
		else if(whichFile.equals(FileIO.VEHICLE))
		{
			list.addAll(fileIO.readFile(FileIO.VEHICLE));
			label.setText("Enter Vehicle:");
		}
		else if(whichFile.equals(FileIO.PRODUCT))
		{
			list.addAll(fileIO.readFile(FileIO.PRODUCT));
			label.setText("Enter Product:");
		}
		
		
		// Sort the list before assigning it to the listView
		Collections.sort(list);
		// Assign the list to the listView
		listView.setItems(list);	
	}
	
	public void setFile(String whichFile)
	{
		this.whichFile = whichFile;
	}
	
	@FXML
	private void handleAdd()
	{	
		// If the textField is not empty
		if(textField.getText() != null && textField.getText().length() != 0)
		{
			// If the list does not contain the text that is entered in the TextField then add it to the list
			if(!list.contains(textField.getText()))
			{
				list.add(textField.getText());
			}
			// Else the list already contains the text that is in the TextField, so display an error message
			else
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.initOwner(dialogStage);
				alert.setTitle("Duplicate Technician");
				alert.setHeaderText("Duplicate Technician");
				alert.setContentText("That technician, " + textField.getText() + ", is already entered!");
				alert.showAndWait();
			}
		}
		// Else the textField is empty, so display an error message
		else
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("Blank Technician");
			alert.setContentText("Technician cannot be blank!");
			alert.showAndWait();
		}
	}
	
	@FXML
	private void handleEdit()
	{
		// Get selected item from listView and load it into the textBox
	}
	
	@FXML
	private void handleDelete()
	{
		
	}
	
	@FXML
	private void handleDone()
	{		
		dialogStage.close();
	}
	
	public void setDialogStage(Stage dialogStage)
	{
		this.dialogStage = dialogStage;
	}
}
