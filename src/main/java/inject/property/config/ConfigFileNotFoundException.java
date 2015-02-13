package inject.property.config;

public class ConfigFileNotFoundException extends Exception {

	private static final long serialVersionUID = -3980131943691922738L;

	public ConfigFileNotFoundException() {
	}

	public ConfigFileNotFoundException(String message) {
		super(message);
	}

	public ConfigFileNotFoundException(Throwable cause) {
		super(cause);
	}

	public ConfigFileNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConfigFileNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
