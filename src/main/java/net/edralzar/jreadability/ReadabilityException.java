package net.edralzar.jreadability;

/**
 * base class for readability related exceptions
 *
 * @author edralzar
 *
 */
public class ReadabilityException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ReadabilityException(String message) {
		super(message);
	}

	public ReadabilityException(Throwable cause) {
		super(cause);
	}

	public ReadabilityException(String message, Throwable cause) {
		super(message, cause);
	}

}
