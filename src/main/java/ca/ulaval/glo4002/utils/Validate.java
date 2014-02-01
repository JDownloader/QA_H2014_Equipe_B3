package ca.ulaval.glo4002.utils;

import ca.ulaval.glo4002.exceptions.InvalidDateFormatException;

public class validate {
	
	public static boolean validateDate(String date) throws InvalidDateFormatException{
		String regexForDateValidation = "((?:2|1)\\d{3}(?:-|\\/)(?:(?:0[1-9])|(?:1[0-2]))(?:-|\\/)(?:(?:0[1-9])|(?:[1-2][0-9])|(?:3[0-1]))(?:T|\\s)(?:(?:[0-1][0-9])|(?:2[0-3])):(?:[0-5][0-9]):(?:[0-5][0-9]))";
		if (date.matches(regexForDateValidation))
			return true;
		else
			throw new InvalidDateFormatException();
	}
	
}
