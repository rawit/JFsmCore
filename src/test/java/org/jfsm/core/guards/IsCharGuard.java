package org.jfsm.core.guards;

import org.jfsm.core.AbstractGuardAdapter;

/**
 * Guard checking for character equality.
 */
public class IsCharGuard extends AbstractGuardAdapter {

	private final char c;

	/**
	 * Constructor for the IsCharGuard class.
	 * 
	 * @param pC The character to test for
	 */
	public IsCharGuard(final char pChar) {
		this.c = pChar;
	}

	/**
	 * Gets the logical expression of this guard as a string.
	 * 
	 * @return The expression value as a string
	 */
	@Override
	public String getExpression() {
		return String.valueOf(this.c);
	}

	/**
	 * Evaluate the expression.
	 * 
	 * @param pEvent the event that was received
	 * @return true if the characters are equal
	 */
	public boolean evaluate(final Object pEvent) {

		if (pEvent instanceof Character) {
			final Character pChar = (Character) pEvent;
			return pChar == c;
		}

		return false;
	}

}
