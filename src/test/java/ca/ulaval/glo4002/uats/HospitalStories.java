package ca.ulaval.glo4002.uats;

import static java.util.Arrays.*;
import static org.jbehave.core.io.CodeLocations.*;
import static org.jbehave.core.reporters.Format.*;

import java.util.List;
import java.util.Locale;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.failures.FailingUponPendingStep;
import org.jbehave.core.i18n.LocalizedKeywords;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.parsers.RegexStoryParser;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.junit.runner.RunWith;

import ca.ulaval.glo4002.uats.runners.JettyTestRunner;
import ca.ulaval.glo4002.uats.steps.*;
import de.codecentric.jbehave.junit.monitoring.JUnitReportingRunner;

@RunWith(JUnitReportingRunner.class)
public class HospitalStories extends JUnitStories {
	private static final LocalizedKeywords keywords = new LocalizedKeywords(Locale.ENGLISH);

	@Override
	public Configuration configuration() {
		StoryReporterBuilder reporterBuilder = new StoryReporterBuilder().withKeywords(keywords).withCodeLocation(codeLocationFromClass(HospitalStories.class))
				.withFailureTrace(true).withFailureTraceCompression(true).withDefaultFormats().withFormats(CONSOLE);

		return new MostUsefulConfiguration().useKeywords(keywords).usePendingStepStrategy(new FailingUponPendingStep())
				.useStoryParser(new RegexStoryParser(keywords)).useStoryLoader(new LoadFromClasspath(HospitalStories.class))
				.useStoryReporterBuilder(reporterBuilder);
	}

	@Override
	public InjectableStepsFactory stepsFactory() {
		return new InstanceStepsFactory(configuration(), new JettyTestRunner(), new HttpResponseSteps(), new PrescriptionSteps(), new PatientSteps(), 
				new DrugSteps(), new InterventionSteps(), new SurgicalToolSteps());
	}

	@Override
	protected List<String> storyPaths() {
		return new StoryFinder().findPaths(codeLocationFromClass(this.getClass()).getFile(), asList("**/*.story", "*.story"), null);
	}
}
