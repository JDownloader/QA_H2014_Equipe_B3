package ca.ulaval.glo4002.rest.requests;

import ca.ulaval.glo4002.exceptions.BadRequestException;

public abstract class MarkInstrumentRequest {
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
	
	protected void validateStatus() throws BadRequestException {
		if(!(this.status.equals(DIRTY_STATUS)
			|| this.status.equals(USED_ON_PATIENT_STATUS)
			|| this.status.equals(UNUSED_STATUS)))
			throw new BadRequestException(INVALID_OR_INCOMPLETE_REQUEST_CODE,INVALID_OR_INCOMPLETE_REQUEST_MESSAGE);
	}
	
	abstract void validateRequestParameters() throws BadRequestException;
	
	public String getTypecode() {
		return typecode;
	}
	
	public String getStatus() {
		return status;
	}
	
	public String getSerialNumber() {
		return serialNumber;
	}
	
}
