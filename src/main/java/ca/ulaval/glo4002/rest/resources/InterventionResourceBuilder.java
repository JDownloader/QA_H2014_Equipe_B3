package ca.ulaval.glo4002.rest.resources;

import ca.ulaval.glo4002.rest.requestparsers.intervention.CreateInterventionRequestParserFactory;
import ca.ulaval.glo4002.rest.requestparsers.surgicaltool.CreateSurgicalToolRequestParserFactory;
import ca.ulaval.glo4002.rest.requestparsers.surgicaltool.ModifySurgicalToolRequestParserFactory;
import ca.ulaval.glo4002.services.InterventionService;

public class InterventionResourceBuilder {
	protected InterventionService service;
	protected CreateInterventionRequestParserFactory createInterventionRequestParserFactory;
	protected CreateSurgicalToolRequestParserFactory createSurgicalToolRequestParserFactory;
	protected ModifySurgicalToolRequestParserFactory modifySurgicalToolRequestParserFactory;

	public InterventionResourceBuilder service(InterventionService service) {
		this.service = service;
		return this;
	}

	public InterventionResourceBuilder createInterventionRequestParserFactory(CreateInterventionRequestParserFactory createInterventionRequestParserFactory) {
		this.createInterventionRequestParserFactory = createInterventionRequestParserFactory;
		return this;
	}

	public InterventionResourceBuilder createSurgicalToolRequestParserFactory(CreateSurgicalToolRequestParserFactory createSurgicalToolRequestParserFactory) {
		this.createSurgicalToolRequestParserFactory = createSurgicalToolRequestParserFactory;
		return this;
	}

	public InterventionResourceBuilder modifySurgicalToolRequestParserFactory(ModifySurgicalToolRequestParserFactory modifySurgicalToolRequestParserFactory) {
		this.modifySurgicalToolRequestParserFactory = modifySurgicalToolRequestParserFactory;
		return this;
	}

	public InterventionResource build() {
		InterventionResource interventionResource = new InterventionResource(this);
		if (service == null || createInterventionRequestParserFactory == null || createSurgicalToolRequestParserFactory == null
				|| modifySurgicalToolRequestParserFactory == null) {
			throw new IllegalStateException();
		}
		return interventionResource;
	}
}
