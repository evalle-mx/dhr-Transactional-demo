package net.tce.exception;

/**
 * Duplica la funcionalidad de excepcion en TransactionalStructured original
 * @author dothr
 *
 */
@SuppressWarnings("serial")
public class SystemTCEException extends Exception {
	private String cveError = "002";
	
	public SystemTCEException(String msg) {
		super(msg);
		
	}
	public SystemTCEException(String msg, Throwable causa) {
		super(msg,causa);
	}
	public SystemTCEException(String cveError, String msg) {
		super(msg);
		this.cveError= cveError;
	}
	public String getCveError() {
		return cveError;
	}
}
