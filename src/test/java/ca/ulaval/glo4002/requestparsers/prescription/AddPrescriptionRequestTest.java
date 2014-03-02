package ca.ulaval.glo4002.requestparsers.prescription;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.*;

import ca.ulaval.glo4002.rest.requestparsers.prescription.AddPrescriptionRequestParser;

public class AddPrescriptionRequestTest {
	
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
		createPrescriptionRequest();
	}
	
	private void createPrescriptionRequest() throws Exception {
		addPrescriptionRequest = new AddPrescriptionRequestParser(jsonRequest);
	}
	
	@Test
	public void validatesGoodRequestCorrectly() throws Exception {
		createPrescriptionRequest();
	}
	
	@Test
	public void validatesGoodRequestWithDrugNameCorrectly() throws Exception {
		swapDinForDrugName();
		createPrescriptionRequest();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void disallowsEmptyDrugName() throws Exception {
		jsonRequest.remove("din");
		jsonRequest.put("nom", "");
		createPrescriptionRequest();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void disallowsUnspecifiedDrugAndDrugNameParameters() throws Exception {
		jsonRequest.remove("din");
		createPrescriptionRequest();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void disallowsDrugAndDrugNameParametersBothSpecified() throws Exception {
		jsonRequest.put("nom", SAMPLE_DRUG_NAME_PARAMETER);
		createPrescriptionRequest();
	}
	
	@Test(expected = JSONException.class)
	public void disallowsUnspecifiedStaffMemberParameter() throws Exception {
		jsonRequest.remove("intervenant");
		createPrescriptionRequest();
	}
	
	@Test(expected = JSONException.class)
	public void disallowsUnspecifiedRenewalsParameter() throws Exception {
		jsonRequest.remove("renouvellements");
		createPrescriptionRequest();
	}
	
	@Test(expected = Exception.class)
	public void disallowsUnspecifiedDateParameter() throws Exception {
		jsonRequest.remove("date");
		createPrescriptionRequest();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void disallowsNegativeStaffMemberParameter() throws Exception {
		jsonRequest.put("intervenant", "-1");
		createPrescriptionRequest();
	}
	
	@Test(expected = Exception.class)
	public void disallowsNegativeRenewalsParameter() throws Exception {
		jsonRequest.put("renouvellements", "-1");
		createPrescriptionRequest();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void disallowsNegativePatientParameter() throws Exception {
		jsonRequest.put("nopatient", "-1");
		createPrescriptionRequest();
	}

	@Test(expected = ParseException.class)
	public void disallowsInvalidDateParameter() throws Exception {
		jsonRequest.put("date", SAMPLE_INVALID_DATE_PARAMETER);
		createPrescriptionRequest();
	}
	
	@Test
	public void allowsMinimumDinParameter() throws Exception {
		jsonRequest.put("din", MIN_DIN_PARAMETER);
		createPrescriptionRequest();
	}
	
	@Test
	public void allowsMinimumStaffMemberParameter() throws Exception {
		jsonRequest.put("intervenant", MIN_STAFF_MEMBER_PARAMETER);
		createPrescriptionRequest();
	}
	
	@Test
	public void allowsMinimumRenewalsParameter() throws Exception {
		jsonRequest.put("renouvellements", MIN_RENEWALS_PARAMETER);
		createPrescriptionRequest();
	}
	
	@Test
	public void allowsMinimumPatientNumberParameter() throws Exception {
		jsonRequest.put("nopatient", MIN_PATIENT_NUMBER_PARAMETER);
		createPrescriptionRequest();
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
		createPrescriptionRequest();
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
