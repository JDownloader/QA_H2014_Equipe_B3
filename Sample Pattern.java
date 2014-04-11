//ca.ulaval.glo4002.rest.resources

@Path("patient/{patient_number: [0-9]+}/prescriptions/")
public class PatientResource {

	private PatientService patientService; 

	public PatientResource() {
		this.patientService = new PatientService();
	}

	public PatientResource(PatientService patientService) {
		this.patientService = patientService;
	}

	@PathParam("patient_number")
	private int patientNumber;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response post(PrescriptionCreationDto prescriptionCreationDto) throws Exception {
		try {
			prescriptionCreationDto.setPatientNumber(patientNumber);
			patientService.createPrescription(prescriptionCreationDto, new PrescriptionCreationDtoValidator(), new PrescriptionAssembler());
			return Response.status(Status.CREATED).build();
		} catch (ServiceRequestException e) {
			return BadRequestJsonResponseBuilder.build(e.getInternalCode(), e.getMessage());
		}
	}
}

//ca.ulaval.glo4002.services

public class PatientService {
	public static final String ERROR_SERVICE_REQUEST_EXCEPTION_PRES001 = "PRES001";

	private PatientRepository patientRepository;
	private PrescriptionRepository prescriptionRepository;
	private DrugRepository drugRepository;
	private EntityManager entityManager;
	private EntityTransaction entityTransaction;
	
	public PatientService() {
		patientRepository = new HibernatePatientRepository();
		prescriptionRepository =  new HibernatePrescriptionRepository();
		drugRepository =  new HibernateDrugRepository();
		entityManager = new EntityManagerProvider().getEntityManager();
		entityTransaction = entityManager.getTransaction();
	}
	
	public PatientService(PatientRepository patientRepository, PrescriptionRepository prescriptionRepository, DrugRepository drugRepository, EntityManager entityManager) {
		this.patientRepository = patientRepository;
		this.prescriptionRepository =  prescriptionRepository;
		this.drugRepository =  drugRepository;
		this.entityManager = entityManager;
		this.entityTransaction = entityManager.getTransaction();
	}

	public void createPrescription(PrescriptionCreationDto prescriptionCreationDto, PrescriptionCreationDtoValidator prescriptionCreationDtoValidator, PrescriptionAssembler prescriptionAssembler) throws ServiceRequestException {
		try {
			prescriptionCreationDtoValidator.validate(prescriptionCreationDto);
			entityTransaction.begin();
			
			doCreatePrescription(prescriptionCreationDto, prescriptionAssembler);
			
			entityTransaction.commit();
		} catch (Exception e) {
			throw new ServiceRequestException(ERROR_SERVICE_REQUEST_EXCEPTION_PRES001, e.getMessage());
		} finally {
			if (entityTransaction.isActive()) {
				entityTransaction.rollback();
			}
		}
	}

	//Method name is JNI approved //TODO: Remove this line
	protected void doCreatePrescription(PrescriptionCreationDto prescriptionCreationDto, PrescriptionAssembler prescriptionAssembler) {
		Prescription prescription = prescriptionAssembler.assemblePrescription(prescriptionCreationDto, drugRepository);
		prescriptionRepository.persist(prescription);
		Patient patient = patientRepository.getById(prescriptionCreationDto.getPatientNumber());
		patient.addPrescription(prescription);
		patientRepository.update(patient);
	}

}

//ca.ulaval.glo4002.services.assemblers

public class PrescriptionAssembler {

	public Prescription assemblePrescription(PrescriptionCreationDto prescriptionCreationDto, DrugRepository drugRepository) {
		Prescription prescription;

		if (prescriptionCreationDto.getDin() != null) {
			prescription = new Prescription(
					getDrug(prescriptionCreationDto, drugRepository),
					prescriptionCreationDto.getRenewals(), 
					prescriptionCreationDto.getDate(),
					new StaffMember(prescriptionCreationDto.getStaffMember()));
		} else {
			prescription = new Prescription(
					prescriptionCreationDto.getDrugName(),
					prescriptionCreationDto.getRenewals(), 
					prescriptionCreationDto.getDate(),
					new StaffMember(prescriptionCreationDto.getStaffMember()));
		}

		return prescription;
	}

	private Drug getDrug(PrescriptionCreationDto prescriptionCreationDto, DrugRepository drugRepository) {
		return drugRepository.getByDin(new Din(prescriptionCreationDto.getDin()));
	}
}

//ca.ulaval.glo4002.services.dto

@SuppressWarnings("unused")
public class PrescriptionCreationDto {
	private String din;
	@JsonProperty("nom")
	private String drugName;
	@JsonProperty("intervenant")
	private Integer staffMember; //TODO: Change to string; Impacts StaffMember -> Surgeon -> Intervention;
	@JsonProperty("renouvellements")
	private Integer renewals;
	private Date date;
	@JsonIgnore
	private Integer patientNumber;
	
	public String getDin() {
		return din;
	}
	
	public String getDrugName() {
		return drugName;
	}
	
	public Integer getStaffMember() {
		return staffMember;
	}

	public Integer getRenewals() {
		return renewals;
	}
	
	public Date getDate() {
		return date;
	}

	public Integer getPatientNumber() {
		return patientNumber;
	}

	public void setPatientNumber(Integer patientNumber) { //Required to set @PathParam value
		this.patientNumber = patientNumber;
	}
}

//ca.ulaval.glo4002.services.dto.validators

public class PrescriptionCreationDtoValidator {
	public void validate(PrescriptionCreationDto prescriptionCreationDto) {
		if (prescriptionCreationDto.getRenewals() == null) {
			throw new PrescriptionCreationException("Parameter 'renouvellements' is required.");
		} else if (prescriptionCreationDto.getRenewals() < 0) {
			throw new PrescriptionCreationException("Parameter 'renouvellements' must be greater or equal to 0.");
		}	else if (prescriptionCreationDto.getDate() == null) {
			throw new PrescriptionCreationException("Parameter 'date' is required.");
		} else if (prescriptionCreationDto.getStaffMember() == null) {
			throw new PrescriptionCreationException("Parameter 'intervenant' is required.");
		} 
		validateDinAndName(prescriptionCreationDto);
	}
	
	private void validateDinAndName(PrescriptionCreationDto prescriptionCreationDto) {
		boolean hasDrugName = !StringUtils.isBlank(prescriptionCreationDto.getDrugName());
		boolean hasDin = prescriptionCreationDto.getDin() != null;

		if (hasDrugName && hasDin) {
			throw new PrescriptionCreationException("Either parameter 'din' or 'nom' must be specified, but not both.");
		} else if (!hasDrugName && !hasDin) {
			throw new PrescriptionCreationException("Parameter 'din' or 'nom' must be specified.");
		}
	}
}