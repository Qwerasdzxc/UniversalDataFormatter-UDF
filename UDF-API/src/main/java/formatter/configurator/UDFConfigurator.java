package formatter.configurator;

public class UDFConfigurator {
	
	private static UDFConfigurator instance;
	
	private String savePath = System.getProperty("user.dir") + "/src/main/resources";
	
	private int entityLimitPerFile = 3;
	
	private boolean autoIncrementIds;
	
	public String getSavePath() {
		return savePath;
	}
	
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public int getEntityLimitPerFile() {
		return entityLimitPerFile;
	}

	public void setEntityLimitPerFile(int entityLimitPerFile) {
		this.entityLimitPerFile = entityLimitPerFile;
	}
	
	private UDFConfigurator() {}

	public static UDFConfigurator getInstance() {
		if (instance == null)
			instance = new UDFConfigurator();
		
		return instance;
	}

	public boolean isAutoIncrementIds() {
		return autoIncrementIds;
	}

	public void setAutoIncrementIds(boolean autoIncrementIds) {
		this.autoIncrementIds = autoIncrementIds;
	}
}
