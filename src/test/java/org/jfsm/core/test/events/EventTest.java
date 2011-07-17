package org.jfsm.core.test.events;

import org.jfsm.core.events.Event;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test code for the Event class.
 */
public class EventTest {

    /**
     * Test getters with argument.
     */
    @Test
    public void testGettersWithArgument() {

        final Object[] arguments = new Object[] { new Object() };
        final Event event = new Event(Object.class, arguments);

        Assert.assertTrue(event.getType().equals(Object.class.getName()));
        Assert.assertTrue(event.getArguments() == arguments);

    }

    /**
     * Test getters with no argument.
     */
    @Test
    public void testGettersNoArgument() {

        try {
            new Event((Class<?>) null, null);
            Assert.fail("Event( ( Class )null,null); should have thrown IllegalArgumentException");
        } catch (final IllegalArgumentException iae) {
            Assert.assertTrue(true);
        }

    }

    /**
     * Constructor for the Test object.
     */
    @Test
    public void testConstructorArgs() {

        try {
            new Event((Class<?>) null, null);
            Assert.fail("Event( ( Class )null,null); should have thrown IllegalArgumentException");
        } catch (final IllegalArgumentException iae) {
            Assert.assertTrue(true);
        }

        try {
            new Event((String) null, null);
            Assert.fail("Event( ( String )null,null); should have thrown IllegalArgumentException");
        } catch (final IllegalArgumentException iae) {
            Assert.assertTrue(true);
        }

    }

}
