package org.jfsm.basic;

import org.jfsm.ActionI;

/**
 * Adapter for the ActionI interface. Implements all methods with default handling. Can be sub classed by classes that
 * do not want to implement all methods in the ActionI interface
 */
public abstract class ActionAdapter implements ActionI {

    /** Set to true after it has been executed. */
    private boolean hasBeenExecuted = false;

    /**
     * Check if the action has been executed.
     * 
     * @return the hasBeenExecuted
     */
    public boolean hasBeenExecuted() {
        return this.hasBeenExecuted;
    }

    /**
     * Set the hasBeenExecuted attribute.
     * 
     * @param pHasBeenExecuted value
     */
    public void setHasBeenExecuted(final boolean pHasBeenExecuted) {
        this.hasBeenExecuted = pHasBeenExecuted;
    }

    /**
     * Gets the Expression attribute of the ActionI object.
     * 
     *@return The Expression value
     */
    public String getExpression() {
        return org.jfsm.basic.JFsmUtilities.removePackagePrefix(this.getClass().getName());
    }

}
