package production.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class is used to read and write to the configuration files (technician.txt, vehicles.txt, products.txt).  These files are used to store a list of the available technicians, vehicles,
 * and products.
 * @author Drew
 *
 */
public class FileIO {
	
	public static final String TECHNICIANS_FILE = "/resources/technicians.txt";
	public static final String VEHICLES_FILE = "/resources/vehicles.txt";
	public static final String PRODUCTS_FILE = "/resources/products.txt";
	
	public FileIO()
	{
		
	}
	
	/**
	 * This method reads the file line by line and creates an Observable list.  Each line in the file is stored as an object in the list.
	 * @param fileName
	 * @return the ObservableList object
	 */
	public ObservableList<String> readFile(String fileName)
	{
		ObservableList<String> list = FXCollections.observableArrayList();
		
		try
		{
			URL url = null;
			// Determine which file needs to be read and set the URL accordingly
			if(fileName == TECHNICIANS_FILE)
			{
				url = this.getClass().getResource(TECHNICIANS_FILE);
			}
			else if(fileName == VEHICLES_FILE)
			{
				url = this.getClass().getResource(VEHICLES_FILE);
			}
			else if(fileName == PRODUCTS_FILE)
			{
				url = this.getClass().getResource(PRODUCTS_FILE);
			}
			
			// Build the Scanner to parse the file line by line
			File file = new File(url.toURI());
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			Scanner fileScanner = new Scanner(br);
			
			String temp = "";
			
			while(fileScanner.hasNext())
			{
				temp = fileScanner.nextLine();
				
				if(temp != null && temp != "")
				{
					list.add(temp);
				}
			}
			fileScanner.close();
			
			return list;
		}
		catch(FileNotFoundException | URISyntaxException e)
		{
			e.printStackTrace();
			
			return null;
		}
	}
	
	/**
	 * This method will take an ObservableList and write its items to the given file.  It will write one list item per line.
	 * @param list
	 * @param fileName
	 */
	public void saveFile(ObservableList<String> list, String fileName)
	{
		try
		{
			URL url = null;
			if(fileName == TECHNICIANS_FILE)
			{
				url = this.getClass().getResource(TECHNICIANS_FILE);
			}
			else if(fileName == VEHICLES_FILE)
			{
				url = this.getClass().getResource(VEHICLES_FILE);
			}
			else if(fileName == PRODUCTS_FILE)
			{
				url = this.getClass().getResource(PRODUCTS_FILE);
			}
			
			File file = new File(url.toURI());
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter printWriter = new PrintWriter(bw);
			
			for(String item : list)
			{
				printWriter.println(item);
			}
			
			printWriter.close();
		}
		catch(URISyntaxException | IOException e)
		{
			
		}
	}
}
