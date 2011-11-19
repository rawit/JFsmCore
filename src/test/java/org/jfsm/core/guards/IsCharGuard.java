package org.jfsm.core.guards;

import org.jfsm.core.annotations.GuardMethod;


/**
 * Guard checking for character equality.
 */
public class IsCharGuard {

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
	 * Evaluate the expression.
	 * 
	 * @param pEvent the event that was received
	 * @return true if the characters are equal
	 */
	@GuardMethod
	public boolean evaluate(final Object pEvent) {

		if (pEvent instanceof Character) {
			final Character pChar = (Character) pEvent;
			return pChar == c;
		}

		return false;
	}

}
