package router;

public class RouterConfig {

	private boolean cacheOperations = false;
	
	public void setCacheOperations(boolean cacheOperations) {
		this.cacheOperations = cacheOperations;
	}

	public boolean isCacheOperations() {
		return cacheOperations;
	}
	
	
}
