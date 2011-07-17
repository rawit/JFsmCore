package org.jfsm.core;

import org.jfsm.Context;
import org.jfsm.GuardConditionI;
import org.jfsm.JFsmException;

/**
 * Adapter for the GuardConditionI interface. Implements all methods with default handling. Can be sub classed by
 * classes that do not want to implement all methods in the GuardConditionI interface.
 * 
 */
public abstract class GuardAdapter implements GuardConditionI {

    /**
     * {@inheritDoc}
     */
    public String getExpression() {
        return org.jfsm.core.JFsmUtilities.removePackagePrefix(this.getClass().getName());
    }

    /**
     * {@inheritDoc}
     */
    public void setContext(final Context fsmContext) throws JFsmException {
        
    }

}
