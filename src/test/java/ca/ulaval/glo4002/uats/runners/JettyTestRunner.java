package ca.ulaval.glo4002.uats.runners;

import org.eclipse.jetty.server.Server;
import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.BeforeStories;

import ca.ulaval.glo4002.contexts.DemoRepositoryFiller;
import ca.ulaval.glo4002.rest.JettyServer;

public class JettyTestRunner {
	public static final int JETTY_TEST_PORT = 8080;
	private Server server;

	@BeforeStories
	public void startJetty() throws Exception {
		DemoRepositoryFiller.fillRepositories();
		
		server = new JettyServer().create(JETTY_TEST_PORT);
		server.start();
	}

	@AfterStories
	public void stopJetty() throws Exception {
		server.stop();
	}
}
