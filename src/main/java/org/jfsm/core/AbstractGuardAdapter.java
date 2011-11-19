package org.jfsm.core;

import org.jfsm.GuardConditionI;

/**
 * Adapter for the GuardConditionI interface. Implements all methods with
 * default handling. Can be sub classed by classes that do not want to implement
 * all methods in the GuardConditionI interface.
 * 
 */
public abstract class AbstractGuardAdapter implements GuardConditionI {

	/**
	 * {@inheritDoc}
	 */
	public String getExpression() {
		return org.jfsm.core.JFsmUtilities.removePackagePrefix(this.getClass().getName());
	}

	public abstract boolean evaluate(Object event);

}
