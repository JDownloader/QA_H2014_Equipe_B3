package ca.ulaval.glo4002.services.intervention;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Date;

import javax.persistence.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.domain.intervention.*;
import ca.ulaval.glo4002.domain.patient.Patient;
import ca.ulaval.glo4002.domain.patient.PatientRepository;
import ca.ulaval.glo4002.domain.surgicaltool.*;
import ca.ulaval.glo4002.exceptions.ServiceRequestException;
import ca.ulaval.glo4002.rest.requestparsers.intervention.CreateInterventionRequestParser;
import ca.ulaval.glo4002.rest.requestparsers.surgicaltool.*;

@RunWith(MockitoJUnitRunner.class)
public class InterventionServiceTest {

}
