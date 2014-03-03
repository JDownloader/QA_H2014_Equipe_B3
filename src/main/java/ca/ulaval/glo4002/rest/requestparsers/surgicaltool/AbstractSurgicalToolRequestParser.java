package ca.ulaval.glo4002.rest.requestparsers.surgicaltool;

import org.apache.commons.lang3.StringUtils;

import ca.ulaval.glo4002.domain.surgicaltool.SurgicalToolStatus;

public abstract class AbstractSurgicalToolRequestParser {
	public static String SERIAL_NUMBER_PARAMETER_NAME = "noserie";
	public static String INTERVENTION_NUMBER_PARAMETER_NAME = "nointervention";
	public static String STATUS_PARAMETER_NAME = "statut";
	public static String TYPECODE_PARAMETER_NAME = "typecode";

	protected SurgicalToolStatus status;
	protected String typeCode;
	protected String serialNumber;
	protected int interventionNumber;

	public String getTypeCode() {
		return this.typeCode;
	}

	public SurgicalToolStatus getStatus() {
		return this.status;
	}

	public String getSerialNumber() {
		return this.serialNumber;
	}

	public int getInterventionNumber() {
		return this.interventionNumber;
	}

	public boolean hasSerialNumber() {
		return !StringUtils.isBlank(this.serialNumber);
	}

}
