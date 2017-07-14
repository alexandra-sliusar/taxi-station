package ua.taxistation.exceptions;

public class ServerAppException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ServerAppException(String messageKey) {
		super(messageKey);
	}

	public ServerAppException(Throwable cause) {
		super(cause);
	}

	public ServerAppException(String messageKey, Throwable cause) {
		super(messageKey, cause);
	}

}
