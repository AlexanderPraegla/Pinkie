package de.altenerding.biber.pinkie.business.nuLiga.entity;

public class NuLigaException extends RuntimeException {

	public NuLigaException() {
	}

	public NuLigaException(String message) {
		super(message);
	}

	public NuLigaException(String message, Throwable cause) {
		super(message, cause);
	}

	public NuLigaException(Throwable cause) {
		super(cause);
	}

	public NuLigaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
