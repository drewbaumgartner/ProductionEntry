package production.controller;

import java.util.Collections;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
	
	private String fileName;
	private String fileType; // This will hold "Vehicle" || "Technician" || "Product"
	private FileIO fileIO;
	private Stage dialogStage;

	ObservableList<String> list = FXCollections.observableArrayList();
	
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
		
		if(fileName.equals(FileIO.TECHNICIANS_FILE))
		{
			list.addAll(fileIO.readFile(FileIO.TECHNICIANS_FILE));
			textFieldLabel.setText("Enter Technician:");
			
			if(fileName.contains("technician"))
			{
				fileType = "technician";
			}
		}
		else if(fileName.equals(FileIO.VEHICLES_FILE))
		{
			list.addAll(fileIO.readFile(FileIO.VEHICLES_FILE));
			textFieldLabel.setText("Enter Vehicle:");
			
			if(fileName.contains("vehicle"))
			{
				fileType = "vehicle";
			}
		}
		else if(fileName.equals(FileIO.PRODUCTS_FILE))
		{
			list.addAll(fileIO.readFile(FileIO.PRODUCTS_FILE));
			textFieldLabel.setText("Enter Product:");
			
			if(fileName.contains("product"))
			{
				fileType = "product";
			}
		}
		
		// Sort the list before assigning it to the listView
		Collections.sort(list);
		// Assign the list to the listView
		listView.setItems(list);
		// Enables the listView to be editable
		listView.setEditable(true);		
		// Sets the cell Factory to TextFields (each cell in the ListView will be a TextField)
		listView.setCellFactory(TextFieldListCell.forListView());
		
		// This EventHandler will fire when the user attempts to submit a change to an item in the ListView
		listView.setOnEditCommit(new EventHandler<ListView.EditEvent<String>>(){

			@Override
			public void handle(EditEvent<String> event) {
				
				// If the list does not have a value that is equal to the new value then attempt to add the new value to the list
				if(!list.contains(event.getNewValue()))
				{
					// If the new value is empty, do not save the changes
					if(event.getNewValue().equals("") || event.getNewValue().length() == 0)
					{
						statusLabel.setText("Changes not saved.  Item cannot be blank!");
					}
					else
					{
						listView.getItems().set(event.getIndex(), event.getNewValue());
					}	
				}
				// Else the value that the user typed in already exists somewhere in the list
				else
				{
					statusLabel.setText("Changes not saved.  Duplicate entry found!");
				}
			}	
		});
		
		// This ChangeListener looks to see when the user selects a different item in the ListView.  When they do, it will clear the statusLabel.
		listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				statusLabel.setText("");
			}
			
		});
	}
	
	public void setFile(String fileName)
	{
		this.fileName = fileName;
	}
	
	/**
	 * This method handles the onAction event for the Add button.  It checks the input from the textField and if it is not empty and not a duplicate then it is added to the list/listView.
	 */
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
				alert.setTitle("Duplicate Found");
				alert.setHeaderText("Duplicate " + fileType);
				alert.setContentText("That " + fileType + ", " + textField.getText() + ", is already entered!");
				alert.showAndWait();
			}
		}
		// Else the textField is empty, so display an error message
		else
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("Blank Entry");
			alert.setContentText("The " + fileType + " cannot be blank!");
			alert.showAndWait();
		}
		textField.selectAll();
	}
	
	/**
	 * This method handles the onAction even for the Delete button.  The selected item in the listView is deleted from the list/listView.
	 */
	@FXML
	private void handleDelete()
	{
		if(listView.getSelectionModel().getSelectedIndex() >= 0)
		{
			list.remove(listView.getSelectionModel().getSelectedIndex());
		}
		else
		{
			statusLabel.setText("Cannot delete.  Nothing is selected in the list!");
		}
	}
	
	@FXML
	private void handleDone()
	{	
		FileIO fileIO = new FileIO();
		fileIO.saveFile(list, fileName);
		dialogStage.close();
	}
	
	public void setDialogStage(Stage dialogStage)
	{
		this.dialogStage = dialogStage;
	}
}
