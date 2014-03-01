package ca.ulaval.glo4002.utils;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
		Date parsedValidDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2001-07-04T12:08:56");
		assertEquals(parsedValidDate, CustomDateParser.parseDate(VALID_DATE));
	}

}
