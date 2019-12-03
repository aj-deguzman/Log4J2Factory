package com.wheelhousecu.log4j2;

import org.apache.logging.log4j.Logger;
import org.junit.Test;

import junit.framework.TestCase;

/**
 * Test Log4J is configured correctly
 * 
 * @author adeguzman
 *
 */
public class Log4J2FactoryTest extends TestCase {
	private final String CLASS_NAME = this.getClass().getName();
	private final String APPENDER_PACKAGE = "com.wheelhousecu.log4j2";
	private final String APPENDER_LOG_LEVEL = "DEBUG";
	private final String ROOT_LOG_LEVEL = "DEBUG";
	private final String FILE_PATH = "C:/Program Files/Apache Software Foundation/Tomcat 7.0/logs/";
	private final String FILE_NAME = "Log4J2Utility.log";
	private final Boolean ADDITIVITY = false;
	private Log4J2Factory factory;
	private Log4J2Params params;
	private Logger log;

	@Override
	public void setUp() {
		this.params = new Log4J2Params(CLASS_NAME, APPENDER_PACKAGE, APPENDER_LOG_LEVEL, ROOT_LOG_LEVEL, FILE_PATH,
				FILE_NAME, ADDITIVITY);
		this.factory = new Log4J2Factory(params);
		this.log = this.factory.getLogger();
	}

	@Test
	public void testParams() {
		assertEquals(this.params.getClassName(), CLASS_NAME);
		assertEquals(this.params.getAppenderPackage(), APPENDER_PACKAGE);
		assertEquals(this.params.getAppenderLogLevel(), APPENDER_LOG_LEVEL);
		assertEquals(this.params.getRootLogLevel(), ROOT_LOG_LEVEL);
		assertEquals(this.params.getFilePath(), FILE_PATH);
		assertEquals(this.params.getFileName(), FILE_NAME);
		assertEquals(this.params.isAdditivty(), ADDITIVITY);

		System.out.println("Class Name ----> " + this.params.getClassName());
		System.out.println("Package ----> " + this.params.getAppenderPackage());
		System.out.println("Appender Log Level ----> " + this.params.getAppenderLogLevel());
		System.out.println("Root Log Level ----> " + this.params.getRootLogLevel());
		System.out.println("File Path ----> " + this.params.getFilePath());
		System.out.println("File Name ----> " + this.params.getFileName());
		System.out.println("Additivity ----> " + this.params.isAdditivty());
	}

	@Test
	public void testLogs() {
		this.log.debug("Debug Message Logged !!!");
		this.log.info("Info Message Logged !!!");
		this.log.warn("Warn Message Logged !!!");
		this.log.error("Error Message Logged !!!");
	}

	@Test
	public void testGetConfiguration() {
		System.out.println("Configuration ----> " + this.factory.getConfiguration());
	}
}
