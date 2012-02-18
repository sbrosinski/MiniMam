package mam.config;

import org.apache.commons.lang.builder.ToStringBuilder;

public class MamConfig {

	private float jpegQuality;
	private String srcFolder;
	private boolean debug, cacheOperations;
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public float getJpegQuality() {
		return jpegQuality;
	}

	public void setJpegQuality(float jpegQuality) {
		this.jpegQuality = jpegQuality;
	}

	public String getSrcFolder() {
		return srcFolder;
	}

	public void setSrcFolder(String srcFolder) {
		this.srcFolder = srcFolder;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public boolean isCacheOperations() {
		return cacheOperations;
	}

	public void setCacheOperations(boolean cacheOperations) {
		this.cacheOperations = cacheOperations;
	}

	
}
