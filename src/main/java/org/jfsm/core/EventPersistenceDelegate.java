package org.jfsm.core;

import java.beans.DefaultPersistenceDelegate;
import java.beans.Encoder;
import java.beans.Expression;

import org.jfsm.core.events.Event;

/**
 * The persistence delegate for the Event class.
 */
public class EventPersistenceDelegate extends DefaultPersistenceDelegate {

    /**
     * Build an instantiation expression for the Event class.
     * 
     *@param oldInstance The old instance of the Event
     *@param out The encoder
     *@return The expression for the instantiation
     */
    protected Expression instantiate(final Object oldInstance, final Encoder out) {
        super.instantiate(oldInstance, out);

        return new Expression(oldInstance, oldInstance.getClass(), "new", new Object[] { ((Event) oldInstance)
                        .getType() });
    }

}
