package org.jfsm.core.test.events;

import org.jfsm.core.events.FsmPropertyChangeEvent;

/**
 * Property change event handler for the TestBean class.
 * 
 * @author rwe
 *
 */
public class MyBeanPropertyChangeEvent extends FsmPropertyChangeEvent {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     * 
     * @param testBean the bean to test
     */
    public MyBeanPropertyChangeEvent(final TestBean testBean) {
        testBean.addPropertyChangeListener(this);
    }

    /**
     * Constructor. 
     * 
     * @param testBean the bean to test
     * @param propertyName the name of the property to watch
     */
    public MyBeanPropertyChangeEvent(final TestBean testBean, final String propertyName) {
        testBean.addPropertyChangeListener(propertyName, this);
    }

}
