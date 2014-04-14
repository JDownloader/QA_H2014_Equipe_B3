package ca.ulaval.glo4002.rest;

import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import ca.ulaval.glo4002.rest.filters.EntityManagerContextFilter;

public class JettyServer {
	private static final String REST_PACKAGE = "ca.ulaval.glo4002.rest.resources";
	
    public Server create(int httpPort) throws Exception {
        Server server = new Server(httpPort);
        ServletContextHandler servletContextHandler = new ServletContextHandler(server, "/");
        servletContextHandler.addFilter(EntityManagerContextFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));
        configureRestInterface(servletContextHandler);
        return server;
    }

    private void configureRestInterface(ServletContextHandler servletContextHandler) {
        ServletContainer container = new ServletContainer(new ResourceConfig().packages(REST_PACKAGE));
        ServletHolder jerseyServletHolder = new ServletHolder(container);
        servletContextHandler.addServlet(jerseyServletHolder, "/*");
    }
}
