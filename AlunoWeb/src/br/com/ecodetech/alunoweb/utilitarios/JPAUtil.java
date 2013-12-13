package br.com.ecodetech.alunoweb.utilitarios;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.apache.log4j.Logger;

@RequestScoped
public class JPAUtil {

	@Inject
	private transient Logger logger;
	
	@PersistenceUnit(unitName="alunoweb")
	private EntityManagerFactory emf;

	@Produces
	@RequestScoped
	public EntityManager getEntityManager() {
		EntityManager em = emf.createEntityManager();
		logger.info("EntityManager iniciado: "+em);
		return em;
	}

	public void close(@Disposes EntityManager em) {
		logger.info("EntityManager encerrado: "+em);
		em.close();
	}
}
