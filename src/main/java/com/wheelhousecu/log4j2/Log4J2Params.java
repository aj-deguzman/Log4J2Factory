package com.wheelhousecu.log4j2;

/**
 * Log4J2 Configuration Parameters
 * 
 * @author adeguzman
 */
public class Log4J2Params {
	private String className = "";
	private String appenderPackage = "";
	private String appenderLogLevel = "";
	private String rootLogLevel = "";
	private String filePath = "";
	private String fileName = "";
	private Boolean additivity = false;

	/**
	 * Construct required Log4J2 configuration parameters upon instantiation
	 * 
	 * @param className
	 * @param appenderPackage
	 * @param appenderLogLevel
	 * @param rootLogLevel
	 * @param filePath
	 * @param fileName
	 * @param additivity
	 */
	public Log4J2Params(String className, String appenderPackage, String appenderLogLevel, String rootLogLevel,
			String filePath, String fileName, Boolean additivity) {
		this.setClassName(className);
		this.setAppenderPackage(appenderPackage);
		this.setAppenderLogLevel(appenderLogLevel);
		this.setRootLogLevel(rootLogLevel);
		this.setFilePath(filePath);
		this.setFileName(fileName);
		this.setAdditivity(additivity);
	}

	protected String getClassName() {
		return this.className;
	}

	protected String getFileName() {
		return this.fileName;
	}

	protected String getFilePath() {
		return this.filePath;
	}

	protected String getAppenderLogLevel() {
		return this.appenderLogLevel;
	}

	protected String getRootLogLevel() {
		return this.rootLogLevel;
	}

	protected String getAppenderPackage() {
		return this.appenderPackage;
	}

	protected Boolean isAdditivty() {
		return this.additivity;
	}

	protected void setClassName(String className) {
		this.className = className;
	}

	protected void setFileName(String fileName) {
		this.fileName = fileName;
	}

	protected void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	protected void setAppenderLogLevel(String appenderLogLevel) {
		this.appenderLogLevel = appenderLogLevel;
	}

	protected void setRootLogLevel(String rootLogLevel) {
		this.rootLogLevel = rootLogLevel;
	}

	protected void setAppenderPackage(String appenderPackage) {
		this.appenderPackage = appenderPackage;
	}

	protected void setAdditivity(Boolean additivity) {
		this.additivity = additivity;
	}
}
