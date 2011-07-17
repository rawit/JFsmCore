package org.jfsm.basic.test.events;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * A test bean with a simple field 'x' with set'ers and get'ers and property change support.
 * 
 * @author rwe
 *
 */
public class TestBean {

    private int x;

    private PropertyChangeSupport support = new PropertyChangeSupport(1);

    /**
     * Add a property change listener.
     * 
     * @param listener the listener
     */
    public void addPropertyChangeListener(final PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    /**
     * Add a property change listener.
     * 
     * @param propertyName the property name
     * @param listener the listener
     */
    public void addPropertyChangeListener(final String propertyName, final PropertyChangeListener listener) {
        support.addPropertyChangeListener(propertyName, listener);
    }

    /**
     * Remove a property change listener.
     * 
     * @param propertyName the property name
     * @param listener the listener
     */
    public void removePropertyChangeListener(final String propertyName, final PropertyChangeListener listener) {
        support.removePropertyChangeListener(propertyName, listener);
    }

    /**
     * Remove a property change listener.
     * 
     * @param listener the listener
     */
    public void removePropertyChangeListener(final PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }


    /**
     * Set a value for x.
     * 
     * @param newX the new value
     */
    public void setX(final int newX) {
        support.firePropertyChange("x", x, newX);
        this.x = newX;
    }

    /**
     * Get the x value.
     * 
     * @return the value
     */
    public int getX() {
        return x;
    }

}
