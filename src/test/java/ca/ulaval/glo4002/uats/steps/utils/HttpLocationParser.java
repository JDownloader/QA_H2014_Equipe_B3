package ca.ulaval.glo4002.uats.steps.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jayway.restassured.response.Response;

public class HttpLocationParser {
	private static final int SURGICAL_TOOL_ID_GROUP_INDEX = 1;
	private static final int INTERVENTION_ID_GROUP_INDEX = 1;
	private static final String INTERVENTION_LOCATION_REGEX = "\\S+/interventions/(\\d+)";
	private static final String SURGICAL_TOOL_LOCATION_REGEX = "\\S+/interventions/\\d+/instruments/\\S+/(\\d+)";
	private static final String LOCATION_HEADER_NAME = "location";

	public static Integer parseInterventionIdFromResponse(Response response) {
		String group = getSingleGroupFromRegexPattern(response.getHeader(LOCATION_HEADER_NAME), INTERVENTION_LOCATION_REGEX, INTERVENTION_ID_GROUP_INDEX);
		return Integer.parseInt(group);
	}

	public static Integer parseSurgicalToolIdFromResponse(Response response) {
		String group = getSingleGroupFromRegexPattern(response.getHeader(LOCATION_HEADER_NAME), SURGICAL_TOOL_LOCATION_REGEX, SURGICAL_TOOL_ID_GROUP_INDEX);
		return Integer.parseInt(group);
	}

	private static String getSingleGroupFromRegexPattern(String input, String regex, int groupIndex) {
		try {
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(input);
			matcher.find();
			return matcher.group(groupIndex);
		} catch (Exception e) {
			throw new IllegalArgumentException("Regex pattern matching failed.", e);
		}
	}
}
