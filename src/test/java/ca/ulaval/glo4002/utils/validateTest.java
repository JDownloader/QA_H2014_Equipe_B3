package ca.ulaval.glo4002.utils;

import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Test;

import ca.ulaval.glo4002.exceptions.InvalidDateFormatException;

public class validateTest {
	
	public static final String VALID_DATE = "2001-07-04T12:08:56";
	public static final String INVALID_DATE = "28-04-1989234-23";
	
	@Test(expected = InvalidDateFormatException.class)
	public void invalidDateReturnExeption()
			throws InvalidDateFormatException, ParseException {
		validate.validateDate(INVALID_DATE);
	}
	
	@Test
	public void validDateReturnTrue()
			throws InvalidDateFormatException, ParseException {
		boolean result = validate.validateDate(VALID_DATE);
		assertTrue(result);
	}

}
