package ca.ulaval.glo4002.rest.requestparsers.surgicaltool;

import ca.ulaval.glo4002.exceptions.BadRequestException;

public abstract class MarkInstrumentRequestParser {
	public String INTERVENTION_NUMBER_PARAMETER = "nointervention";
	public String STATUS_PARAMETER = "statut";
	public String SERIAL_NUMBER_PARAMETER = "noserie";
	public String TYPECODE_PARAMETER = "typecode";
	
	public String INVALID_OR_INCOMPLETE_REQUEST_CODE = "INT010";
	public String INVALID_OR_INCOMPLETE_REQUEST_MESSAGE = "Données invalides ou incomplètes";
	
	public String DIRTY_STATUS = "SOUILLE";
	public String USED_ON_PATIENT_STATUS = "UTILISE_PATIENT";
	public String UNUSED_STATUS = "INUTILISE";
	
	protected String status;
	protected String typecode;
	protected String serialNumber;
	protected int interventionId;
	
	protected void validateStatus() throws BadRequestException {
		if(!(this.status.equals(DIRTY_STATUS)
			|| this.status.equals(USED_ON_PATIENT_STATUS)
			|| this.status.equals(UNUSED_STATUS)))
			throw new BadRequestException(INVALID_OR_INCOMPLETE_REQUEST_CODE,INVALID_OR_INCOMPLETE_REQUEST_MESSAGE);
	}
	
	abstract void validateRequestParameters() throws BadRequestException;
	
	public String getTypecode() {
		return this.typecode;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public String getSerialNumber() {
		return this.serialNumber;
	}
	
	public int getInterventionId() {
		return this.interventionId;
	}
}
