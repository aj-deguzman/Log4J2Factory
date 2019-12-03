package com.wheelhousecu.log4j2;

import java.net.URI;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.LayoutComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.LoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.RootLoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
import org.json.JSONObject;

/**
 * Configures, instantiates Log4J2, and Logger
 * 
 * @author adeguzman
 */
public class Log4J2Factory extends ConfigurationFactory {
	private Log4J2Params params = null;

	/**
	 * Constructs configuration factory
	 * 
	 * @param Log4J2Params object
	 */
	public Log4J2Factory(Log4J2Params params) {
		this.params = params;
		ConfigurationFactory.setConfigurationFactory(this);
	}

	/**
	 * Builds Log4J2 Configuration
	 * 
	 * @param name
	 * @param builder
	 * @return configuration build
	 */
	private Configuration createConfiguration(final String name, ConfigurationBuilder<BuiltConfiguration> builder) {
		// Log output pattern
		LayoutComponentBuilder layout = builder.newLayout("PatternLayout").addAttribute("pattern",
				"[%-5p] %d{yyyy-MM-dd HH:mm:ss} %c{1}:%L - %m%n");

		// Log file output
		AppenderComponentBuilder rollingFile = builder.newAppender("rolling", "RollingFile");
		rollingFile.addAttribute("fileName", this.params.getFilePath() + this.params.getFileName());
		rollingFile.addAttribute("filePattern",
				this.params.getFilePath() + this.params.getFileName() + "-%d{yyyy-MM-dd}.log");
		rollingFile.add(layout);

		// File roll definition
		ComponentBuilder triggeringPolicies = builder.newComponent("Policies")
				.addComponent(builder.newComponent("TimeBasedTriggeringPolicy").addAttribute("interval", "1"));
		rollingFile.addComponent(triggeringPolicies);
		builder.add(rollingFile);

		// Console output
		AppenderComponentBuilder console = builder.newAppender("console", "Console");
		console.add(layout);
		builder.add(console);

		// Logger properties
		LoggerComponentBuilder logger = builder.newLogger(params.getAppenderPackage(),
				getLogLevel(this.params.getAppenderLogLevel()));
		logger.addAttribute("additivity", this.params.isAdditivty());
		logger.add(builder.newAppenderRef("rolling"));
		logger.add(builder.newAppenderRef("console"));
		builder.add(logger);

		RootLoggerComponentBuilder rootLogger = builder.newRootLogger(getLogLevel(this.params.getRootLogLevel()));
		rootLogger.add(builder.newAppenderRef("rolling"));
		rootLogger.add(builder.newAppenderRef("console"));
		builder.add(rootLogger);

		// Test XML configuration output
		//		try {
		//			builder.writeXmlConfiguration(System.out);
		//		} catch (IOException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}

		return builder.build();
	}

	/**
	 * Returns log level object based on the log level string that is passed to this
	 * class
	 * 
	 * @param level
	 * @return log level object
	 */
	private static Level getLogLevel(String level) {
		Level logLevel = null;

		switch (level) {
			case "FATAL":
				logLevel = Level.FATAL;
				break;
			case "ERROR":
				logLevel = Level.ERROR;
				break;
			case "WARN":
				logLevel = Level.WARN;
				break;
			case "INFO":
				logLevel = Level.INFO;
				break;
			case "DEBUG":
				logLevel = Level.DEBUG;
				break;
			case "TRACE":
				logLevel = Level.TRACE;
				break;
			case "ALL":
				logLevel = Level.ALL;
				break;
			default:
				logLevel = Level.WARN;
				break;
		}

		return logLevel;
	}

	/**
	 * Get logger object
	 * 
	 * @return Logger object
	 */
	public Logger getLogger() { return LogManager.getLogger(params.getClassName()); }

	private String getConfigurationName() {
		final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		final Configuration config = ctx.getConfiguration();

		return config.getName();
	}

	/**
	 * Get Log4J2 parameters as JSONObject
	 * 
	 * @return parameters as JSONObject
	 */
	public JSONObject jsonObject() {
		JSONObject obj = new JSONObject();

		obj.put("className", params.getClassName());
		obj.put("appenderPackage", params.getAppenderPackage());
		obj.put("fileName", params.getFileName());
		obj.put("filePath", params.getFilePath());
		obj.put("appenderLogLevel", params.getAppenderLogLevel());
		obj.put("rootLogLevel", params.getRootLogLevel());
		obj.put("additivity", params.isAdditivty());

		return obj;
	}

	/**
	 * Get configuration
	 * 
	 * @return configuration
	 */
	public Configuration getConfiguration() {
		return getConfiguration((LoggerContext) LogManager.getContext(false), this.getConfigurationName(), null);
	}

	@Override
	public Configuration getConfiguration(LoggerContext loggerContext, ConfigurationSource source) {
		return getConfiguration(loggerContext, source.toString(), null);
	}

	@Override
	public Configuration getConfiguration(final LoggerContext loggerContext, final String name,
			final URI configLocation) {
		ConfigurationBuilder<BuiltConfiguration> builder = newConfigurationBuilder();
		return createConfiguration(name, builder);
	}

	@Override
	protected String[] getSupportedTypes() { return new String[] { "*" }; }
}
