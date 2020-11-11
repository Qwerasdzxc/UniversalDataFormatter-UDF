package formatter;

/**
 *  Configuration for {@link formatter.DataFormatter}
 */
class UDFConfigurator {
	
	private static UDFConfigurator instance;
	
	private String savePath = System.getProperty("user.dir") + "/src/main/resources";
	
	private int entityLimitPerFile = 3;
	
	private boolean autoIncrementIds = true;
	
	/**
	 * <p>Returns storage location of stored {@link formatter.models.Entity}s</p>
	 * @return savePath	storage location
	 */
	public String getSavePath() {
		return savePath;
	}
	
	/**
	 * <p>Sets storage location of stored {@link formatter.models.Entity}s</p>
	 * @param savePath	storage location
	 */
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	/**
	 * <p>Returns all {@link formatter.models.Entity}s from storage</p>
	 * @return entities		filtered entities from storage
	 */
	public int getEntityLimitPerFile() {
		return entityLimitPerFile;
	}

	/**
	 * <p>Sets the maximum number of {@link formatter.models.Entity}s per file</p>
	 * @param entityLimitPerFile	maximum number of entities per file
	 */
	public void setEntityLimitPerFile(int entityLimitPerFile) {
		this.entityLimitPerFile = entityLimitPerFile;
	}
	
	/**
	 * <p>Returns whether unique identifiers get automatically generated or must be
	 * provided along with {@link formatter.models.Entity} details</p>
	 * @return autoIncrementIds	is auto-increment enabled
	 */
	public boolean isAutoIncrementIds() {
		return autoIncrementIds;
	}

	/**
	 * <p>Sets whether unique identifiers get automatically generated or must be
	 * provided along with {@link formatter.models.Entity} details</p>
	 * @return autoIncrementIds	is auto-increment enabled
	 */
	public void setAutoIncrementIds(boolean autoIncrementIds) {
		this.autoIncrementIds = autoIncrementIds;
	}
	
	private UDFConfigurator() {}

	public static UDFConfigurator getInstance() {
		if (instance == null)
			instance = new UDFConfigurator();
		
		return instance;
	}
}
