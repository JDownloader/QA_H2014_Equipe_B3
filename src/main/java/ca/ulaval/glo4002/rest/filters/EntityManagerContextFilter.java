package ca.ulaval.glo4002.rest.filters;

import java.io.IOException;

import javax.persistence.*;
import javax.servlet.*;

import ca.ulaval.glo4002.entitymanager.EntityManagerFactoryProvider;
import ca.ulaval.glo4002.entitymanager.EntityManagerProvider;

public class EntityManagerContextFilter implements Filter {
	private EntityManagerFactory entityManagerFactory;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		entityManagerFactory = EntityManagerFactoryProvider.getFactory();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		EntityManager entityManager = null;
		EntityTransaction entityTransaction = null;

		try {
			entityManager = entityManagerFactory.createEntityManager();
			EntityManagerProvider.setEntityManager(entityManager);

			chain.doFilter(request, response);
		} finally {
			if (entityTransaction != null && entityTransaction.isActive()) {
				entityTransaction.rollback();
			}
			if (entityManager != null) {
				entityManager.close();
			}
			EntityManagerProvider.clearEntityManager();
		}

	}

	@Override
	public void destroy() {
		entityManagerFactory.close();
	}
}
