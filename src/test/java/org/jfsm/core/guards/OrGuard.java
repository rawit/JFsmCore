package org.jfsm.core.guards;

import org.jfsm.GuardConditionI;
import org.jfsm.core.AbstractGuardAdapter;

/**
 * Guard for a logical 'or' between two guard conditions.
 */
public class OrGuard extends AbstractGuardAdapter {

	private final GuardConditionI gc1;

	private final GuardConditionI gc2;

	/**
	 * Constructor for the Or guard.
	 * 
	 * @param gc1 first guard condition
	 * @param gc2 second guard condition
	 */
	public OrGuard(final GuardConditionI gc1, final GuardConditionI gc2) {
		this.gc1 = gc1;
		this.gc2 = gc2;
	}

	/**
	 * Gets the logical expression of this guard as a string.
	 * 
	 * @return The expression value as a string
	 */
	@Override
	public String getExpression() {
		return gc1.getExpression() + " or " + gc2.getExpression();
	}

	/**
	 * Evaluate the expression.
	 * 
	 * @param event the event that was received
	 * @return true if the or'ed guards are true
	 */
	public boolean evaluate(final Object event) {
		return gc1.evaluate(event) || gc2.evaluate(event);
	}

}
