package ca.ulaval.glo4002.utils;

import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Test;

import ca.ulaval.glo4002.exeptions.InvalidDateFormat;

public class validateTest {
	
	public static final String VALID_DATE = "2001-07-04T12:08:56";
	public static final String INVALID_DATE = "28-04-1989234-23";
	
	@Test(expected = InvalidDateFormat.class)
	public void invalidDateReturnExeption()
			throws InvalidDateFormat, ParseException {
		validate.validateDate(INVALID_DATE);
	}
	
	@Test
	public void validDateReturnTrue()
			throws InvalidDateFormat, ParseException {
		boolean result = validate.validateDate(VALID_DATE);
		assertTrue(result);
	}

}
