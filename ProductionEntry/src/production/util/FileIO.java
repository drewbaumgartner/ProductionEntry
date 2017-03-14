package production.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FileIO {
	
	private final String TECHNICIANS_FILE = "/resources/technicians.txt";
	private final String VEHICLES_FILE = "/resources/vehicles.txt";
	private final String PRODUCTS_FILE = "/resources/products.txt";
	public static final String TECHNICIAN = "technician";
	public static final String VEHICLE = "vehicle";
	public static final String PRODUCT = "product";
	
	public FileIO()
	{
		
	}
	
	public ObservableList<String> readFile(String fileName)
	{
		ObservableList<String> list = FXCollections.observableArrayList();
		
		try
		{
			URL url = null;
			if(fileName == TECHNICIAN)
			{
				url = this.getClass().getResource(TECHNICIANS_FILE);
			}
			else if(fileName == VEHICLE)
			{
				url = this.getClass().getResource(VEHICLES_FILE);
			}
			else if(fileName == PRODUCT)
			{
				url = this.getClass().getResource(PRODUCTS_FILE);
			}
			
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
}
