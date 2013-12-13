package br.com.ecodetech.alunoweb.interceptor;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.transaction.UserTransaction;

import org.apache.log4j.Logger;

@SuppressWarnings("serial")
@Interceptor
@Transactional
public class TransactionInterceptor implements Serializable {

	//@Inject
	//private EntityManager em;
	
	@Resource
    UserTransaction transaction;
	
	@Inject
	private transient Logger logger;
	
	@AroundInvoke
	public Object intercept(InvocationContext context) throws Exception{
		transaction.begin();
		logger.info("TransactionInterceptor.intercept(): Abertura de transação "+transaction);
		//em.getTransaction().begin();
		
		Object result = context.proceed();
		
		//em.getTransaction().commit();
		logger.info("TransactionInterceptor.intercept(): Encerramento de transação "+transaction);
		transaction.commit();
		return result;
	}
}
