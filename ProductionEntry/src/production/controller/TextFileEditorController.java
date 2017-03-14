package production.controller;

import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.ListView.EditEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import production.util.FileIO;

public class TextFileEditorController {

	@FXML
	private ListView<String> listView;
	@FXML
	private TextField textField;
	@FXML
	private Label textFieldLabel;
	@FXML
	private Label statusLabel;
	
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
			textFieldLabel.setText("Enter Technician:");
		}
		else if(whichFile.equals(FileIO.VEHICLE))
		{
			list.addAll(fileIO.readFile(FileIO.VEHICLE));
			textFieldLabel.setText("Enter Vehicle:");
		}
		else if(whichFile.equals(FileIO.PRODUCT))
		{
			list.addAll(fileIO.readFile(FileIO.PRODUCT));
			textFieldLabel.setText("Enter Product:");
		}
		
		
		// Sort the list before assigning it to the listView
		Collections.sort(list);
		// Assign the list to the listView
		listView.setItems(list);
		// Enables the listView to be editable
		listView.setEditable(true);		
		// Sets the cell Factory to TextFields (each cell in the ListView will be a TextField)
		listView.setCellFactory(TextFieldListCell.forListView());
		
		listView.setOnEditCommit(new EventHandler<ListView.EditEvent<String>>(){

			@Override
			public void handle(EditEvent<String> event) {
				
				if(!list.contains(event.getNewValue()))
				{
					listView.getItems().set(event.getIndex(), event.getNewValue());
				}
				else
				{
					statusLabel.setText("Changes not saved.  Duplicate entry found!");
				}
			}	
		});
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
