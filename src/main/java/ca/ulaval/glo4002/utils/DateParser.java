package ca.ulaval.glo4002.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateParser {

	public static final String DATE_PARSE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

	public static Date parseDate(String date) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(DATE_PARSE_FORMAT);
		format.setLenient(false);
		return format.parse(date);
	}
}
