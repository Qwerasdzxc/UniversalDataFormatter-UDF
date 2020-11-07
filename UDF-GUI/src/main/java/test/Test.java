package test;

import formatter.DataFormatter;
import manager.UDFManager;

public class Test {

	public static void main(String[] args) {
		try {
			Class.forName("formatter.FormatterImpl");
			
			DataFormatter formatter = UDFManager.getFormatter();
			formatter.save("Data from GUI");
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
