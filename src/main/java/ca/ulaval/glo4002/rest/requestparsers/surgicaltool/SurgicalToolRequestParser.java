package ca.ulaval.glo4002.rest.requestparsers.surgicaltool;

import ca.ulaval.glo4002.domain.surgicaltool.SurgicalToolStatus;

public interface SurgicalToolRequestParser {

	String getTypeCode();

	SurgicalToolStatus getStatus();

	String getSerialNumber();

	int getInterventionNumber();

	boolean hasSerialNumber();

}
