package production.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import production.util.FileIO;

public class TextFileEditorController {

	@FXML
	private ListView<String> listView;
	@FXML
	private TextField textField;
	
	private String whichFile;
	
	private FileIO fileIO;
	
	ObservableList<String> list = FXCollections.observableArrayList();
	
	public TextFileEditorController(String whichFile)
	{
		this.whichFile = whichFile;
	}
	
	@FXML
	private void initialize()
	{
		fileIO = new FileIO();
		
		if(whichFile.equals(FileIO.TECHNICIAN))
		{
			list.addAll(fileIO.readFile(FileIO.TECHNICIAN));
		}
		else if(whichFile.equals(FileIO.VEHICLE))
		{
			list.addAll(fileIO.readFile(FileIO.VEHICLE));
		}
		else if(whichFile.equals(FileIO.PRODUCT))
		{
			list.addAll(fileIO.readFile(FileIO.PRODUCT));
		}
		
	}
	

	
}
