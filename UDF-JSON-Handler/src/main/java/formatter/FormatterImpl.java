package formatter;

import manager.UDFManager;

public class FormatterImpl extends DataFormatter {
	
	static {
		UDFManager.registerFormatter(new FormatterImpl());
	}

	@Override
	public void save(String data) {
		System.out.println("JSON Handler: " + data);
	}
}
