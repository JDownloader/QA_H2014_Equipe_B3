package ca.ulaval.glo4002.requests;

import java.text.ParseException;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.*;

import ca.ulaval.glo4002.rest.requests.AddPrescriptionRequest;

//@RunWith(MockitoJUnitRunner.class)
public class AddPrescriptionRequestTest {
	
	private static final String SAMPLE_DATE_PARAMETER = "2001-07-04T12:08:56";
	private static final String SAMPLE_INVALID_DATE_PARAMETER = "2001-07-0412:08:56";
	private static final int SAMPLE_RENEWALS_PARAMETER = 2;
	private static final String SAMPLE_DRUG_NAME_PARAMETER = "drug_name";
	private static final int SAMPLE_STAFF_MEMBER_PARAMETER = 3;
	private static final int SAMPLE_DIN_PARAMETER = 3;
	private static final String SAMPLE_PATIENT_NUMBER_PARAMETER = "3";
	private static final String MIN_PATIENT_NUMBER_PARAMETER = "0";
	private static final int MIN_RENEWALS_PARAMETER = 0;
	private static final int MIN_STAFF_MEMBER_PARAMETER = 0;
	private static final int MIN_DIN_PARAMETER = 0;
	
	private JSONObject jsonRequest = new JSONObject();
	
	@Before
	public void setup() {
		jsonRequest.put("intervenant", SAMPLE_STAFF_MEMBER_PARAMETER);
		jsonRequest.put("date", SAMPLE_DATE_PARAMETER);
		jsonRequest.put("renouvellements", SAMPLE_RENEWALS_PARAMETER);
		jsonRequest.put("din", SAMPLE_DIN_PARAMETER);
	}
	
	@Test
	public void validatesGoodRequestCorrectly() throws Exception {
		new AddPrescriptionRequest(jsonRequest, SAMPLE_PATIENT_NUMBER_PARAMETER);
	}
	
	@Test
	public void validatesGoodRequestWithDrugNameCorrectly() throws Exception {
		jsonRequest.remove("din");
		jsonRequest.put("nom", SAMPLE_DRUG_NAME_PARAMETER);
		new AddPrescriptionRequest(jsonRequest, SAMPLE_PATIENT_NUMBER_PARAMETER);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void disallowsEmptyDrugName() throws Exception {
		jsonRequest.remove("din");
		jsonRequest.put("nom", "");
		new AddPrescriptionRequest(jsonRequest, SAMPLE_PATIENT_NUMBER_PARAMETER);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void disallowsUnspecifiedDrugAndDrugNameParameters() throws Exception {
		jsonRequest.remove("din");
		new AddPrescriptionRequest(jsonRequest, SAMPLE_PATIENT_NUMBER_PARAMETER);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void disallowsDrugAndDrugNameParametersBothSpecified() throws Exception {
		jsonRequest.put("nom", SAMPLE_DRUG_NAME_PARAMETER);
		new AddPrescriptionRequest(jsonRequest, SAMPLE_PATIENT_NUMBER_PARAMETER);
	}
	
	@Test(expected = JSONException.class)
	public void disallowsUnspecifiedStaffMemberParameter() throws Exception {
		jsonRequest.remove("intervenant");
		new AddPrescriptionRequest(jsonRequest, SAMPLE_PATIENT_NUMBER_PARAMETER);
	}
	
	@Test(expected = JSONException.class)
	public void disallowsUnspecifiedRenewalsParameter() throws Exception {
		jsonRequest.remove("renouvellements");
		new AddPrescriptionRequest(jsonRequest, SAMPLE_PATIENT_NUMBER_PARAMETER);
	}
	
	@Test(expected = Exception.class)
	public void disallowsUnspecifiedDateParameter() throws Exception {
		jsonRequest.remove("date");
		new AddPrescriptionRequest(jsonRequest, SAMPLE_PATIENT_NUMBER_PARAMETER);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void disallowsNegativeStaffMemberParameter() throws Exception {
		jsonRequest.put("intervenant", "-1");
		new AddPrescriptionRequest(jsonRequest, SAMPLE_PATIENT_NUMBER_PARAMETER);
	}
	
	@Test(expected = Exception.class)
	public void disallowsNegativeRenewalsParameter() throws Exception {
		jsonRequest.put("renouvellements", "-1");
		new AddPrescriptionRequest(jsonRequest, SAMPLE_PATIENT_NUMBER_PARAMETER);
	}
	
	@Test(expected = JSONException.class)
	public void disallowsNegativePatientParameter() throws Exception {
		jsonRequest.remove("intervenant");
		new AddPrescriptionRequest(jsonRequest, "-1");
	}

	@Test(expected = ParseException.class)
	public void disallowsInvalidDateParameter() throws Exception {
		jsonRequest.put("date", SAMPLE_INVALID_DATE_PARAMETER);
		new AddPrescriptionRequest(jsonRequest, SAMPLE_PATIENT_NUMBER_PARAMETER);
	}
	
	@Test
	public void allowsMinimumDinValue() throws Exception {
		jsonRequest.put("din", MIN_DIN_PARAMETER);
		new AddPrescriptionRequest(jsonRequest, SAMPLE_PATIENT_NUMBER_PARAMETER);
	}
	
	@Test
	public void allowsMinimumStaffMemberValue() throws Exception {
		jsonRequest.put("intervenant", MIN_STAFF_MEMBER_PARAMETER);
		new AddPrescriptionRequest(jsonRequest, SAMPLE_PATIENT_NUMBER_PARAMETER);
	}
	
	@Test
	public void allowsMinimumRenewalsValue() throws Exception {
		jsonRequest.put("renouvellements", MIN_RENEWALS_PARAMETER);
		new AddPrescriptionRequest(jsonRequest, SAMPLE_PATIENT_NUMBER_PARAMETER);
	}
	
	@Test
	public void allowsMinimumPatientNumberValue() throws Exception {
		new AddPrescriptionRequest(jsonRequest, MIN_PATIENT_NUMBER_PARAMETER);
	}
}
