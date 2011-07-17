package org.jfsm.core;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.jfsm.JFsmException;
import org.jfsm.events.After;
import org.jfsm.events.TimerEvent;
import org.jfsm.events.When;
import org.jfsm.fsm.JFsm;

/**
 * A timer task that will send the submitted Timer Event to the state machine.
 */
public class JFsmTimerTask extends TimerTask {

    private static final Logger LOGGER = Logger.getLogger(JFsmTimerTask.class);
    
    private Timer timer;

    private JFsm jFsm;

    private final TimerEvent timerEvent;

    /**
     * Constructor for the JFsmTimerTask object.
     * 
     *@param timerEvent The timer event
     */
    public JFsmTimerTask(final TimerEvent timerEvent) {

        if (timerEvent == null) {
            throw new IllegalArgumentException("Argument \"timerEvent\" is null");
        }

        if (!(timerEvent instanceof After || timerEvent instanceof When)) {
            throw new IllegalArgumentException("Unknown Timer event = " + timerEvent);
        }

        this.timerEvent = timerEvent;

    }

    /**
     * Sets the JFsm attribute of the JFsmTimerTask object.
     * 
     *@param pFsm The new JFsm value
     */
    public void setFsm(final JFsm pFsm) {

        if (pFsm == null) {
            throw new IllegalArgumentException("Argument 'pFsm' is null");
        }

        this.jFsm = pFsm;
    }

    /**
     * Schedule the task.
     */
    public void start() {

        if (timer == null) {
            timer = new Timer();
        }

        if (timerEvent instanceof After) {
            final After after = (After) timerEvent;
            if (after.getRepeat()) {
                timer.schedule(this, after.getDelay(), after.getDelay());
            } else {
                timer.schedule(this, after.getDelay());
            }
        } else if (timerEvent instanceof When) {
            timer.schedule(this, ((When) timerEvent).getTime());
        }
    }

    /**
     * Main processing method for the JFsmTimerTask object.
     */
    public void run() {

        try {
            LOGGER.debug(System.currentTimeMillis() + " TimerTask: input " + timerEvent);
            jFsm.input(timerEvent);
        } catch (final JFsmException fsme) {
            LOGGER.error("Error on input to FSM, exception = ", fsme);
        }

    }

    /**
     * Cancel the task.
     */
    public void stop() {
        LOGGER.debug("stop: ");
        if (timer != null) {
            this.cancel();
        }
    }

}
