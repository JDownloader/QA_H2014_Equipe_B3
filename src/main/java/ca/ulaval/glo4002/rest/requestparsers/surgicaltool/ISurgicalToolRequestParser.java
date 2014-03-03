package ca.ulaval.glo4002.rest.requestparsers.surgicaltool;

import org.json.JSONObject;

import ca.ulaval.glo4002.domain.surgicaltool.SurgicalToolStatus;
import ca.ulaval.glo4002.exceptions.RequestParseException;

public interface ISurgicalToolRequestParser {
	
	public String getTypeCode();
	public SurgicalToolStatus getStatus();
	public String getSerialNumber();
	public int getInterventionNumber();
	public boolean hasSerialNumber();
	
}
