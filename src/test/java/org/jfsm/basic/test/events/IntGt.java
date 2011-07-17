package org.jfsm.basic.test.events;

import java.beans.PropertyChangeEvent;

import org.jfsm.basic.GuardAdapter;

/**
 * A guard condition for 'greater than'. The constructor supplies the limit value, and the event from the
 * <code>evaluate</code> method is the value to test against.
 * 
 * @author rwe
 * 
 */
public class IntGt extends GuardAdapter {

    private final int intValue;

    /**
     * Constructor.
     * 
     * @param pIntValue the limit to test for 'greatness'
     */
    public IntGt(final int pIntValue) {
        this.intValue = pIntValue;
    }

    /**
     * {@inheritDoc}
     */
    public boolean evaluate(final Object event) {

        if (event instanceof PropertyChangeEvent) {
            final PropertyChangeEvent pce = (PropertyChangeEvent) event;
            final Integer newInt = (Integer) pce.getNewValue();
            if (newInt.intValue() > intValue) {
                return true;
            }
        }

        return false;
    }

}
