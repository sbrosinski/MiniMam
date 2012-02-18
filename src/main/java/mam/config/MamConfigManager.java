package mam.config;

public class MamConfigManager {

	private static MamConfigManager instance;
	private MamConfig config;
	
	protected MamConfigManager() {}
	
	public static MamConfigManager get() {
		if (instance == null) {
			instance = new MamConfigManager();
		}
		return instance;
	}

	public MamConfig getConfig() {
		return config;
	}

	public void setConfig(MamConfig config) {
		this.config = config;
	}
	
}
