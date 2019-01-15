package com.az.rest;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;

import com.az.rest.dao.impl.DBManager;

@ApplicationPath("/")
public class AppConfig extends ResourceConfig {

	public AppConfig() {
		packages("com.az.rest.resource");
		register(new LoggingFeature(Logger.getLogger(LoggingFeature.DEFAULT_LOGGER_NAME), Level.INFO,
				LoggingFeature.Verbosity.PAYLOAD_ANY, 10000));		
		DBManager.getInstance();
	}
}
