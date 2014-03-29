package ca.ulaval.glo4002.rest.utils;

import java.text.SimpleDateFormat;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperProvider {
	public static final String DATE_PARSE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
	private static ObjectMapper mapper;
	
	public static ObjectMapper getObjectMapper() {
		synchronized(ObjectMapper.class) {
    		if (mapper == null) {
    			ObjectMapper mapper = new ObjectMapper();
    			mapper.setDateFormat(getDateFormat());
    		}
		}
		
		return mapper;
	}

	private static SimpleDateFormat getDateFormat() {
		SimpleDateFormat format = new SimpleDateFormat(DATE_PARSE_FORMAT);
		format.setLenient(false);
		return format;
	}
}
