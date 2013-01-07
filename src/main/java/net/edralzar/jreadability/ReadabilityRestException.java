package net.edralzar.jreadability;

public class ReadabilityRestException extends ReadabilityException {

	private static final long serialVersionUID = 1L;
	private int code;
	private String detail;

	public ReadabilityRestException(int code, String message) {
		super("Error " + code);
		this.code = code;
		this.detail = message;
	}

	public int getCode() {
		return code;
	}

	public String getDetail() {
		return detail;
	}

}
