package production.util;

import java.time.LocalDate;
import java.time.format.*;

/**
 * Special thanks to Marco Jakob and his tutorial "http://code.makery.ch/library/javafx-8-tutorial/part1/" 
 * 
 * @author Drew
 *
 */
public class DateUtil {
	
	// The date pattern that is used for conversion
	private static final String DATE_PATTERN = "MM-dd-yyyy";
	
	// The date formatter
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);
	
	/**
	 * Returns the given date as a formatted String using the DATE_PATTERN
	 * @param date
	 * @return
	 */
	public static String format(LocalDate date)
	{
		if(date == null)
		{
			return null;
		}
		
		return DATE_FORMATTER.format(date);
	}
	
	/**
	 * Converts a string and converts it into a LocalDate object if its format matches the DATE_PATTERN
	 * @param dateString
	 * @return the formatted date as a LocalDate object, returns null if the String could not be converted to a LocalDate object
	 */
	public static LocalDate parse(String dateString)
	{
		try
		{
			return DATE_FORMATTER.parse(dateString, LocalDate::from);
		}
		catch(DateTimeParseException e)
		{
			return null;
		}
	}
	
	/**
	 * 
	 * @param dateString
	 * @return true if the String is a valid date
	 */
	public static boolean validDate(String dateString)
	{
		return DateUtil.parse(dateString) != null;
	}
}
