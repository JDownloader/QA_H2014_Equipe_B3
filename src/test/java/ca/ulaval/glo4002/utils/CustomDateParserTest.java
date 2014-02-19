package ca.ulaval.glo4002.utils;

import java.text.ParseException;

import org.junit.Test;

public class CustomDateParserTest {

	public static final String VALID_DATE = "2001-07-04T12:08:56";
	public static final String INVALID_DATE = "28-04-1989234-23";

	@Test(expected = ParseException.class)
	public void invalidDateReturnExeption() throws ParseException {
		CustomDateParser.parseDate(INVALID_DATE);
	}

	@Test
	public void validatesDateCorrectly() throws ParseException {
		CustomDateParser.parseDate(VALID_DATE);
	}

}
