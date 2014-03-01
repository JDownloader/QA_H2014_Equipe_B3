package ca.ulaval.glo4002.rest.requests;

import ca.ulaval.glo4002.exceptions.BadRequestException;

public abstract class MarkInstrumentRequest {
	public String STATUS_PARAMETER = "statut";
	public String SERIAL_NUMBER_PARAMETER = "noserie";
	public String TYPECODE_PARAMETER = "typecode";
	
	public String DIRTY_STATUS = "SOUILLE";
	public String USED_ON_PATIENT_STATUS = "UTILISE_PATIENT";
	public String UNUSED_STATUS = "INUTILISE";
	
	protected String status;
	
	protected void validateStatus() throws BadRequestException {
		if(!(this.status.equals(DIRTY_STATUS)
			|| this.status.equals(USED_ON_PATIENT_STATUS)
			|| this.status.equals(UNUSED_STATUS)))
			throw new BadRequestException("INT010","Données invalides ou incomplètes");
	}
	
}
