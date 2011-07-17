package org.jfsm.guards;

import org.jfsm.core.GuardAdapter;

/**
 * Adapter checking if a submitted key is a dot (.).
 */
public class IsChar extends GuardAdapter {

    private final char c;

    /**
     * Constructor for the ButtonSelected object.
     * 
     *@param pC The character to test for
     */
    public IsChar(final char pC) {
        this.c = pC;
    }

    /**
     * Gets the Expression attribute of the ButtonSelected object.
     * 
     *@return The Expression value
     */
    public String getExpression() {
        return String.valueOf(this.c);
    }

    /**
     * Evaluate the expression.
     * 
     *@param event the event that was received
     *@return true if the button is deselected
     */
    public boolean evaluate(final Object event) {

        if (event instanceof Character) {
            final Character keyChar = (Character) event;
            return keyChar == c;
        }

        return false;
    }

}
