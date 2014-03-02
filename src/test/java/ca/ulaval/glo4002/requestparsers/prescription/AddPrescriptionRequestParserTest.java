package ca.ulaval.glo4002.requestparsers.prescription;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;
import org.junit.*;

import ca.ulaval.glo4002.rest.requestparsers.prescription.AddPrescriptionRequestParser;
import ca.ulaval.glo4002.exceptions.*;

public class AddPrescriptionRequestParserTest {
	
	private static final String SAMPLE_DATE_PARAMETER = "2001-07-04T12:08:56";
	private static final String SAMPLE_INVALID_DATE_PARAMETER = "2001-07-0412:08:56";
	private static final int SAMPLE_RENEWALS_PARAMETER = 2;
	private static final String SAMPLE_DRUG_NAME_PARAMETER = "drug_name";
	private static final int SAMPLE_STAFF_MEMBER_PARAMETER = 3;
	private static final int SAMPLE_DIN_PARAMETER = 3;
	private static final String SAMPLE_PATIENT_NUMBER_PARAMETER = "3";
	private static final int SAMPLE_PATIENT_NUMBER = 3;
	private static final String MIN_PATIENT_NUMBER_PARAMETER = "0";
	private static final int MIN_RENEWALS_PARAMETER = 0;
	private static final int MIN_STAFF_MEMBER_PARAMETER = 0;
	private static final int MIN_DIN_PARAMETER = 0;
	
	private AddPrescriptionRequestParser addPrescriptionRequest;
	private JSONObject jsonRequest = new JSONObject();
	
	@Before
	public void setup() throws Exception {
		jsonRequest.put("intervenant", SAMPLE_STAFF_MEMBER_PARAMETER);
		jsonRequest.put("date", SAMPLE_DATE_PARAMETER);
		jsonRequest.put("renouvellements", SAMPLE_RENEWALS_PARAMETER);
		jsonRequest.put("din", SAMPLE_DIN_PARAMETER);
		jsonRequest.put("nopatient", SAMPLE_PATIENT_NUMBER_PARAMETER);
		createRequestParser();
	}
	
	private void createRequestParser() throws Exception {
		addPrescriptionRequest = new AddPrescriptionRequestParser(jsonRequest);
	}
	
	@Test
	public void validatesGoodRequestCorrectly() throws Exception {
		createRequestParser();
	}
	
	@Test
	public void validatesGoodRequestWithDrugNameCorrectly() throws Exception {
		swapDinForDrugName();
		createRequestParser();
	}
	
	@Test(expected = RequestParseException.class)
	public void disallowsEmptyDrugName() throws Exception {
		jsonRequest.remove("din");
		jsonRequest.put("nom", "");
		createRequestParser();
	}
	
	@Test(expected = RequestParseException.class)
	public void disallowsUnspecifiedDrugAndDrugNameParameters() throws Exception {
		jsonRequest.remove("din");
		createRequestParser();
	}
	
	@Test(expected = RequestParseException.class)
	public void disallowsDrugAndDrugNameParametersBothSpecified() throws Exception {
		jsonRequest.put("nom", SAMPLE_DRUG_NAME_PARAMETER);
		createRequestParser();
	}
	
	@Test(expected = RequestParseException.class)
	public void disallowsUnspecifiedStaffMemberParameter() throws Exception {
		jsonRequest.remove("intervenant");
		createRequestParser();
	}
	
	@Test(expected = RequestParseException.class)
	public void disallowsUnspecifiedRenewalsParameter() throws Exception {
		jsonRequest.remove("renouvellements");
		createRequestParser();
	}
	
	@Test(expected = Exception.class)
	public void disallowsUnspecifiedDateParameter() throws Exception {
		jsonRequest.remove("date");
		createRequestParser();
	}
	
	@Test(expected = RequestParseException.class)
	public void disallowsNegativeStaffMemberParameter() throws Exception {
		jsonRequest.put("intervenant", "-1");
		createRequestParser();
	}
	
	@Test(expected = Exception.class)
	public void disallowsNegativeRenewalsParameter() throws Exception {
		jsonRequest.put("renouvellements", "-1");
		createRequestParser();
	}
	
	@Test(expected = RequestParseException.class)
	public void disallowsNegativePatientParameter() throws Exception {
		jsonRequest.put("nopatient", "-1");
		createRequestParser();
	}

	@Test(expected = RequestParseException.class)
	public void disallowsInvalidDateParameter() throws Exception {
		jsonRequest.put("date", SAMPLE_INVALID_DATE_PARAMETER);
		createRequestParser();
	}
	
	@Test
	public void allowsMinimumDinParameter() throws Exception {
		jsonRequest.put("din", MIN_DIN_PARAMETER);
		createRequestParser();
	}
	
	@Test
	public void allowsMinimumStaffMemberParameter() throws Exception {
		jsonRequest.put("intervenant", MIN_STAFF_MEMBER_PARAMETER);
		createRequestParser();
	}
	
	@Test
	public void allowsMinimumRenewalsParameter() throws Exception {
		jsonRequest.put("renouvellements", MIN_RENEWALS_PARAMETER);
		createRequestParser();
	}
	
	@Test
	public void allowsMinimumPatientNumberParameter() throws Exception {
		jsonRequest.put("nopatient", MIN_PATIENT_NUMBER_PARAMETER);
		createRequestParser();
	}
	
	@Test
	public void hasDinReturnsCorrectValue() throws Exception {
		assertTrue(addPrescriptionRequest.hasDin());
	}
	
	@Test
	public void returnsCorrectDin() throws Exception {
		assertEquals(SAMPLE_DIN_PARAMETER, addPrescriptionRequest.getDin());
	}
	
	@Test
	public void returnsCorrectDrugName() throws Exception {
		swapDinForDrugName();
		createRequestParser();
		assertEquals(SAMPLE_DRUG_NAME_PARAMETER, addPrescriptionRequest.getDrugName());
	}
	
	@Test
	public void returnsCorrectStaffMember() throws Exception {
		assertEquals(SAMPLE_STAFF_MEMBER_PARAMETER, addPrescriptionRequest.getStaffMember());
	}
	
	@Test
	public void returnsCorrectPatientNumber() throws Exception {
		assertEquals(SAMPLE_PATIENT_NUMBER, addPrescriptionRequest.getPatientNumber());
	}
	
	@Test
	public void returnsCorrectRenewals() throws Exception {
		assertEquals(SAMPLE_RENEWALS_PARAMETER, addPrescriptionRequest.getRenewals());
	}
	
	@Test
	public void returnsCorrectDate() throws Exception {
		Date sampleDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2001-07-04T12:08:56");
		assertEquals(sampleDate, addPrescriptionRequest.getDate());
	}
	
	private void swapDinForDrugName() {
		jsonRequest.remove("din");
		jsonRequest.put("nom", SAMPLE_DRUG_NAME_PARAMETER);
	}
}
