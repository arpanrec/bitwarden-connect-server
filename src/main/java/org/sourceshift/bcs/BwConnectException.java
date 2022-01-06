package org.sourceshift.bcs;

public class BwConnectException extends Exception {

	private static final long serialVersionUID = -6092061602955349172L;

	public BwConnectException() {
        super();
    }

    public BwConnectException(String message) {
        super(message);
    }

    public BwConnectException(String message, Throwable cause) {
        super(message, cause);
    }

    public BwConnectException(Throwable cause) {
        super(cause);
    }

    protected BwConnectException(String message, Throwable cause,
                                 boolean enableSuppression,
                                 boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
