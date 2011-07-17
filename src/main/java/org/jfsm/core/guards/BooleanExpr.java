package org.jfsm.core.guards;

import org.jfsm.core.GuardAdapter;

/**
 * Adapter checking if a submitted key is a dot (.).
 */
public class BooleanExpr extends GuardAdapter {

	/**
	 * Constructor for the ButtonSelected object.
	 * 
	 */
	public BooleanExpr() {

	}

	/**
	 * Check of the submitted.
	 * 
	 * @param c the character to test
	 * @return the gc
	 */
	public static GuardAdapter isChar(final char c) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Test for equality.
	 * 
	 * @param c1 the first character
	 * @param c2 the second character
	 * @return true if equal, otherwise false
	 */
	public static boolean eq(final char c1, final char c2) {
		return c1 == c2;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean evaluate(final Object event) {
		return eq('2', 'c');
	}

}
