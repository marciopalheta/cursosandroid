package br.com.ecodetech.alunoweb.cdi;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

import org.apache.log4j.Logger;

public class LoggerFactory {

	private InjectionPoint ip;

	@Inject
	private LoggerFactory(InjectionPoint ip) {
		this.ip = ip;
	}
	
	@Produces
	public Logger getLogger(){
		return Logger.getLogger(ip.getMember().getDeclaringClass());
	}
}
