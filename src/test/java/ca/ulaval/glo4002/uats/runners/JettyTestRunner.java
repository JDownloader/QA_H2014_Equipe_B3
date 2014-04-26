package ca.ulaval.glo4002.uats.runners;

import org.eclipse.jetty.server.Server;
import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.BeforeStories;

import ca.ulaval.glo4002.contexts.DemoRepositoryFiller;
import ca.ulaval.glo4002.rest.JettyServer;

public class JettyTestRunner {
	public static final int JETTY_TEST_PORT = 8080;
	private static final String TEST_DRUG_FILE_PATH = "src/test/data/drug.txt";
	private static final String TEST_DRUG_INTERACTION_FILE_PATH = "src/test/data/interactions.txt";
	
	private Server server;

	@BeforeStories
	public void startJetty() throws Exception {
		new DemoRepositoryFiller(TEST_DRUG_FILE_PATH, TEST_DRUG_INTERACTION_FILE_PATH).fillRepositories();
		
		server = new JettyServer().create(JETTY_TEST_PORT);
		server.start();
	}

	@AfterStories
	public void stopJetty() throws Exception {
		server.stop();
	}
}
