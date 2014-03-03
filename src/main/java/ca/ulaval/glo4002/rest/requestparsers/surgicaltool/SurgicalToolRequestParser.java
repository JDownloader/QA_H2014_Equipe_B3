package ca.ulaval.glo4002.rest.requestparsers.surgicaltool;

import ca.ulaval.glo4002.domain.surgicaltool.SurgicalToolStatus;

public interface SurgicalToolRequestParser {
	
	public String getTypeCode();
	public SurgicalToolStatus getStatus();
	public String getSerialNumber();
	public int getInterventionNumber();
	public boolean hasSerialNumber();
	
}
