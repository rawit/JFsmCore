package org.jfsm;

/**
 * FsmException will be thrown by the JFsmEngineI whenever a method is called
 * that leads to an abnormal situation.
 */
public class JFsmException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for the FsmError class.
	 * 
	 * @param message A message describing the exception
	 */
	public JFsmException(final String message) {
		super(message);
	}

}
