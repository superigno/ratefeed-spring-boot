package com.pc.globalpos.ratefeed.model;

/**
 * @author gino.q
 * @date April 8, 2020
 *
 */
public final class ApplicationProperties {

	private final String mailFrom;
	private final String mailTo;
	private final String mailSubject;

	private final String baseDir;
	private final String outputDir;

	private final String sourceName;
	private final String sourceUrl;
	private final String sourceType;

	private final String filename;

	private final int runTimeIntervalInMinute;

	private final int retryLimit;
	private final int retryIntervalInMinute;

	private final String baseCurrency;

	private ApplicationProperties(String mailFrom, String mailTo, String mailSubject, String baseDir, String outputDir, String sourceName,
			String sourceUrl, String sourceType, String filename, int runTimeIntervalInMinute,
			int retryLimit, int retryIntervalInMinute, String baseCurrency) {
		this.mailFrom = mailFrom;
		this.mailTo = mailTo;
		this.mailSubject = mailSubject;
		this.baseDir = baseDir;
		this.outputDir = outputDir;
		this.sourceName = sourceName;
		this.sourceUrl = sourceUrl;
		this.sourceType = sourceType;
		this.filename = filename;
		this.runTimeIntervalInMinute = runTimeIntervalInMinute;
		this.retryLimit = retryLimit;
		this.retryIntervalInMinute = retryIntervalInMinute;
		this.baseCurrency = baseCurrency;
	}

	public String getMailFrom() {
		return mailFrom;
	}

	public String getMailTo() {
		return mailTo;
	}
	
	public String getMailSubject() {
		return mailSubject;
	}

	public String getBaseDir() {
		return baseDir;
	}

	public String getOutputDir() {
		return outputDir;
	}

	public String getSourceName() {
		return sourceName;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public String getSourceType() {
		return sourceType;
	}

	public String getFilename() {
		return filename;
	}

	public int getRunTimeIntervalInMinute() {
		return runTimeIntervalInMinute;
	}

	public int getRetryLimit() {
		return retryLimit;
	}

	public int getRetryIntervalInMinute() {
		return retryIntervalInMinute;
	}

	public String getBaseCurrency() {
		return baseCurrency;
	}

	public static class Builder {
		private String mailFrom;
		private String mailTo;
		private String mailSubject;

		private String baseDir;
		private String outputDir;

		private String sourceName;
		private String sourceUrl;
		private String sourceType;

		private String filename;

		private int runTimeIntervalInMinute;

		private int retryLimit;
		private int retryIntervalInMinute;

		private String baseCurrency;

		public Builder setMailFrom(String mailFrom) {
			this.mailFrom = mailFrom;
			return this;
		}

		public Builder setMailTo(String mailTo) {
			this.mailTo = mailTo;
			return this;
		}
		
		public Builder setMailSubject(String mailSubject) {
			this.mailSubject = mailSubject;
			return this;
		}

		public Builder setBaseDir(String baseDir) {
			this.baseDir = baseDir;
			return this;
		}

		public Builder setOutputDir(String outputDir) {
			this.outputDir = outputDir;
			return this;
		}

		public Builder setSourceName(String sourceName) {
			this.sourceName = sourceName;
			return this;
		}

		public Builder setSourceUrl(String sourceUrl) {
			this.sourceUrl = sourceUrl;
			return this;
		}

		public Builder setSourceType(String sourceType) {
			this.sourceType = sourceType;
			return this;
		}

		public Builder setFilename(String filename) {
			this.filename = filename;
			return this;
		}

		public Builder setRunTimeIntervalInMinute(int runTimeIntervalInMinute) {
			this.runTimeIntervalInMinute = runTimeIntervalInMinute;
			return this;
		}

		public Builder setRetryLimit(int retryLimit) {
			this.retryLimit = retryLimit;
			return this;
		}

		public Builder setRetryIntervalInMinute(int retryIntervalInMinute) {
			this.retryIntervalInMinute = retryIntervalInMinute;
			return this;
		}

		public Builder setBaseCurrency(String baseCurrency) {
			this.baseCurrency = baseCurrency;
			return this;
		}

		public ApplicationProperties build() {
			return new ApplicationProperties(mailFrom, mailTo, mailSubject, baseDir, outputDir, sourceName, sourceUrl, sourceType,
					filename, runTimeIntervalInMinute, retryLimit, retryIntervalInMinute, baseCurrency);
		}

	}

}
