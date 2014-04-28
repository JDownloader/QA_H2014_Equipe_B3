package ca.ulaval.glo4002.uats.steps.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.jayway.restassured.response.Response;

public class HttpLocationParser {
	private static final String INTERVENTION_LOCATION_REGEX = "\\S+/interventions/(\\d+)";
	private static final String SURGICAL_TOOL_LOCATION_REGEX = "\\S+/interventions/\\d+/instruments/\\S+/(\\d+)";
	private static final String LOCATION_HEADER_NAME = "location";

	public static Integer tryParseInterventionIdFromHeader(Response response) {
		String group = getSingleGroupFromRegexPattern(response.getHeader(LOCATION_HEADER_NAME), INTERVENTION_LOCATION_REGEX, 1);
		try {
			return Integer.parseInt(group);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static Integer tryParseSurgicalToolIdFromHeader(Response response) {
		String group = getSingleGroupFromRegexPattern(response.getHeader(LOCATION_HEADER_NAME), SURGICAL_TOOL_LOCATION_REGEX, 1);
		try {
			return Integer.parseInt(group);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	private static String getSingleGroupFromRegexPattern(String input, String regex, int groupIndex) {
		if (!StringUtils.isBlank(input)) {
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(input);
			if (matcher.find()) {
				return matcher.group(groupIndex);
			}
		}
		return null;
	}
}
