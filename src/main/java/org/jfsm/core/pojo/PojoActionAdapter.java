package org.jfsm.core.pojo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.jfsm.core.ActionAdapter;
import org.jfsm.core.annotations.ActionMethod;

/**
 * Action adapter that supports POJO actions.
 * 
 * @author ragnarwestad
 *
 */
public class PojoActionAdapter extends ActionAdapter {

    private Object target;

    private Method method;

    /**
     * Constructor taking an action object as argument. Must have one method annotated with @ActionMethod.
     * 
     * @param pTarget the action POJO
     */
    public PojoActionAdapter(Object pTarget) {
        this(pTarget, null);
    }

    /**
     * Constructor taking an action object and the action method name as an argument.
     * 
     * @param pTarget the action POJO
     * @param pMethodName the action method
     */
    public PojoActionAdapter(Object pTarget, String pMethodName) {
        this.target = pTarget;
        if (pMethodName != null) {
            this.method = findMethod(pTarget, pMethodName);
            return;
        }
        method = findByAnnotation(pTarget);
    }

    private Method findMethod(Object pTarget, String pMethodName) {

        final Method[] methods = pTarget.getClass().getMethods();
        for (Method met : methods) {
            if (met.getName().equals(pMethodName)) {
                return met;
            }
        }
        throw new RuntimeException("Method '" + pMethodName + "' not found.");
    }

    private Method findByAnnotation(Object pTarget) {
        final Method[] methods = pTarget.getClass().getMethods();
        for (Method met : methods) {
            final Annotation[] annotations = met.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation.annotationType().equals(ActionMethod.class)) {
                    return met;
                }                
            }
            
        }
        throw new RuntimeException("No action method annotations found on class: " + pTarget);
    }

    public void execute(Object event) {
        if (method.getParameterTypes().length == 0) {
            try {
                method.invoke(target, (Object[]) null);
            } catch (Exception exc) {
                throw new RuntimeException("Error calling action method= " + method, exc);
            }
            return;
        }
        try {
            method.invoke(target, event);
        } catch (Exception exc) {
            throw new RuntimeException("Error calling action method= " + method, exc);
        }

    }

}
