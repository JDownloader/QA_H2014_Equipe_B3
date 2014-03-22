package ca.ulaval.glo4002.requestparsers.prescription;

import static org.junit.Assert.*;

import java.util.Date;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4002.exceptions.RequestParseException;
import ca.ulaval.glo4002.rest.requestparsers.prescription.AddPrescriptionRequestParser;
import ca.ulaval.glo4002.rest.requestparsers.prescription.AddPrescriptionRequestParserFactory;
import ca.ulaval.glo4002.utils.DateParser;

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

	private AddPrescriptionRequestParser addPrescriptionRequestParser;
	private JSONObject jsonRequest = new JSONObject();

	@Before
	public void init() throws Exception {
		jsonRequest.put(AddPrescriptionRequestParser.STAFF_MEMBER_PARAMETER_NAME, SAMPLE_STAFF_MEMBER_PARAMETER);
		jsonRequest.put(AddPrescriptionRequestParser.DATE_PARAMETER_NAME, SAMPLE_DATE_PARAMETER);
		jsonRequest.put(AddPrescriptionRequestParser.RENEWAL_PARAMETER_NAME, SAMPLE_RENEWALS_PARAMETER);
		jsonRequest.put(AddPrescriptionRequestParser.DIN_PARAMETER_NAME, SAMPLE_DIN_PARAMETER);
		jsonRequest.put(AddPrescriptionRequestParser.PATIENT_NUMBER_PARAMETER_NAME, SAMPLE_PATIENT_NUMBER_PARAMETER);
		createRequestParser();
	}

	private void createRequestParser() throws Exception {
		addPrescriptionRequestParser = new AddPrescriptionRequestParserFactory().getParser(jsonRequest);
	}

	@Test
	public void validatesGoodRequestWithDrugNameCorrectly() throws Exception {
		swapDinForDrugName();
		createRequestParser();
	}

	@Test(expected = RequestParseException.class)
	public void disallowsEmptyDrugName() throws Exception {
		jsonRequest.remove(AddPrescriptionRequestParser.DIN_PARAMETER_NAME);
		jsonRequest.put(AddPrescriptionRequestParser.DRUG_NAME_PARAMETER_NAME, "");
		createRequestParser();
	}

	@Test(expected = RequestParseException.class)
	public void disallowsUnspecifiedDrugAndDrugNameParameters() throws Exception {
		jsonRequest.remove(AddPrescriptionRequestParser.DIN_PARAMETER_NAME);
		createRequestParser();
	}

	@Test(expected = RequestParseException.class)
	public void disallowsDrugAndDrugNameParametersBothSpecified() throws Exception {
		jsonRequest.put(AddPrescriptionRequestParser.DRUG_NAME_PARAMETER_NAME, SAMPLE_DRUG_NAME_PARAMETER);
		createRequestParser();
	}

	@Test(expected = RequestParseException.class)
	public void disallowsUnspecifiedStaffMemberParameter() throws Exception {
		jsonRequest.remove(AddPrescriptionRequestParser.STAFF_MEMBER_PARAMETER_NAME);
		createRequestParser();
	}

	@Test(expected = RequestParseException.class)
	public void disallowsUnspecifiedRenewalsParameter() throws Exception {
		jsonRequest.remove(AddPrescriptionRequestParser.RENEWAL_PARAMETER_NAME);
		createRequestParser();
	}

	@Test(expected = Exception.class)
	public void disallowsUnspecifiedDateParameter() throws Exception {
		jsonRequest.remove(AddPrescriptionRequestParser.DATE_PARAMETER_NAME);
		createRequestParser();
	}

	@Test(expected = RequestParseException.class)
	public void disallowsNegativeStaffMemberParameter() throws Exception {
		jsonRequest.put(AddPrescriptionRequestParser.STAFF_MEMBER_PARAMETER_NAME, "-1");
		createRequestParser();
	}

	@Test(expected = Exception.class)
	public void disallowsNegativeRenewalsParameter() throws Exception {
		jsonRequest.put(AddPrescriptionRequestParser.RENEWAL_PARAMETER_NAME, "-1");
		createRequestParser();
	}

	@Test(expected = RequestParseException.class)
	public void disallowsNegativePatientParameter() throws Exception {
		jsonRequest.put(AddPrescriptionRequestParser.PATIENT_NUMBER_PARAMETER_NAME, "-1");
		createRequestParser();
	}

	@Test(expected = RequestParseException.class)
	public void disallowsInvalidDateParameter() throws Exception {
		jsonRequest.put(AddPrescriptionRequestParser.DATE_PARAMETER_NAME, SAMPLE_INVALID_DATE_PARAMETER);
		createRequestParser();
	}

	@Test
	public void allowsMinimumDinParameter() throws Exception {
		jsonRequest.put(AddPrescriptionRequestParser.DIN_PARAMETER_NAME, MIN_DIN_PARAMETER);
		createRequestParser();
	}

	@Test
	public void allowsMinimumStaffMemberParameter() throws Exception {
		jsonRequest.put(AddPrescriptionRequestParser.STAFF_MEMBER_PARAMETER_NAME, MIN_STAFF_MEMBER_PARAMETER);
		createRequestParser();
	}

	@Test
	public void allowsMinimumRenewalsParameter() throws Exception {
		jsonRequest.put(AddPrescriptionRequestParser.RENEWAL_PARAMETER_NAME, MIN_RENEWALS_PARAMETER);
		createRequestParser();
	}

	@Test
	public void allowsMinimumPatientNumberParameter() throws Exception {
		jsonRequest.put(AddPrescriptionRequestParser.PATIENT_NUMBER_PARAMETER_NAME, MIN_PATIENT_NUMBER_PARAMETER);
		createRequestParser();
	}

	@Test
	public void hasDinReturnsCorrectValue() throws Exception {
		assertTrue(addPrescriptionRequestParser.hasDin());
	}

	@Test
	public void hasDinReturnsCorrectValueWhenNoDin() throws Exception {
		swapDinForDrugName();
		createRequestParser();
		assertFalse(addPrescriptionRequestParser.hasDin());
	}

	@Test
	public void returnsCorrectDin() throws Exception {
		assertEquals(SAMPLE_DIN_PARAMETER, addPrescriptionRequestParser.getDin());
	}

	@Test
	public void returnsCorrectDrugName() throws Exception {
		swapDinForDrugName();
		createRequestParser();
		assertEquals(SAMPLE_DRUG_NAME_PARAMETER, addPrescriptionRequestParser.getDrugName());
	}

	@Test
	public void returnsCorrectStaffMember() throws Exception {
		assertEquals(SAMPLE_STAFF_MEMBER_PARAMETER, addPrescriptionRequestParser.getStaffMember());
	}

	@Test
	public void returnsCorrectPatientNumber() throws Exception {
		assertEquals(SAMPLE_PATIENT_NUMBER, addPrescriptionRequestParser.getPatientNumber());
	}

	@Test
	public void returnsCorrectRenewals() throws Exception {
		assertEquals(SAMPLE_RENEWALS_PARAMETER, addPrescriptionRequestParser.getRenewals());
	}

	@Test
	public void returnsCorrectDate() throws Exception {
		Date sampleDate = DateParser.parseDate(SAMPLE_DATE_PARAMETER);
		assertEquals(sampleDate, addPrescriptionRequestParser.getDate());
	}

	private void swapDinForDrugName() {
		jsonRequest.remove("din");
		jsonRequest.put("nom", SAMPLE_DRUG_NAME_PARAMETER);
	}
}
