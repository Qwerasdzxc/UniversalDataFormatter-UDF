package manager;

import formatter.DataFormatter;

public class UDFManager {

	private static DataFormatter formatter;
	
	public static void registerFormatter(DataFormatter dataFormatter) {
		formatter = dataFormatter;
	}
	
	public static DataFormatter getFormatter() {
		return formatter;
	}
}
