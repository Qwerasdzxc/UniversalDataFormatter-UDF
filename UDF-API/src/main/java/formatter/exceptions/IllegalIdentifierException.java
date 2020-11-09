package formatter.exceptions;

@SuppressWarnings("serial")
public class IllegalIdentifierException extends Exception {

	public IllegalIdentifierException() {
		super("Identifier already exists!");
	}
}
