package ca.ulaval.glo4002.utils;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

public class DateParserTest {

	public static final String SAMPLE_VALID_DATE = "2001-07-04T12:08:56";
	public static final String SAMPLE_INVALID_DATE = "28-04-1989234-23";

	@Test(expected = ParseException.class)
	public void invalidDateReturnExeption() throws ParseException {
		DateParser.parseDate(SAMPLE_INVALID_DATE);
	}

	@Test
	public void validatesDateCorrectly() throws ParseException {
		Date parsedValidDate = new SimpleDateFormat(DateParser.DATE_PARSE_FORMAT).parse(SAMPLE_VALID_DATE);
		assertEquals(parsedValidDate, DateParser.parseDate(SAMPLE_VALID_DATE));
	}

}
