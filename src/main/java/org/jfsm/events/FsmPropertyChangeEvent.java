package org.jfsm.events;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.jfsm.JFsmException;
import org.jfsm.fsm.JFsm;

/**
 * A property change listener event.
 * 
 * @author rwe
 * 
 */
public abstract class FsmPropertyChangeEvent extends Event implements PropertyChangeListener {

    private static final long serialVersionUID = 1L;

    private JFsm jFsm;

    private boolean running = false;

    /**
     * Default constructor.
     */
    public FsmPropertyChangeEvent() {
        super(PropertyChangeEvent.class);
    }

    /**
     * Start the listener.
     */
    public void start() {
        running = true;
    }

    /**
     * Stop the listener.
     */
    public void stop() {
        running = false;
    }

    /**
     * Set the reference to the JFsm.
     * 
     * @param jFsm the value
     */
    public void setJFsm(final JFsm jFsm) {
        this.jFsm = jFsm;
    }

    /**
     * {@inheritDoc}
     */
    public void propertyChange(final PropertyChangeEvent evt) {
        if (!running) {
            return;
        }

        try {
            this.jFsm.input(evt);
        } catch (final JFsmException jfsme) {
            throw new RuntimeException(jfsme);
        }

    }

    /**
     * {@inheritDoc}
     */
    public String toString() {

        return this.getClass().getName();
    }
}
