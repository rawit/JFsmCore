package org.jfsm.core.pojo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.jfsm.core.AbstractGuardAdapter;
import org.jfsm.core.annotations.GuardMethod;

public class PojoGuardAdapter extends AbstractGuardAdapter {

    private Object target;

    private Method method;

    /**
     * Constructor taking an guard object as argument. Must have one method annotated with @GuardMethod.
     * 
     * @param pTarget the guard POJO
     */
    public PojoGuardAdapter(Object pTarget) {
        this(pTarget, null);
    }

    /**
     * Constructor taking an action object and the guard method name as an argument.
     * 
     * @param pTarget the guard POJO
     * @param pMethodName the guard method
     */
    public PojoGuardAdapter(Object pTarget, String pMethodName) {
        if (pTarget == null) {
            throw new IllegalArgumentException("Argument 'pTarget' cannot be null.");
        }
        this.target = pTarget;
        if (pMethodName != null) {
            this.method = findMethod(pTarget, pMethodName);
            return;
        }
        method = findByAnnotation(pTarget);
    }

    public boolean evaluate(Object event) {
        if (method.getParameterTypes().length == 0) {
            try {
                return (Boolean) method.invoke(target, (Object[]) null);
            } catch (Exception exc) {
                throw new RuntimeException("Error calling guard method= " + method, exc);
            }
        }
        try {
            return (Boolean) method.invoke(target, event);
        } catch (Exception exc) {
            throw new RuntimeException("Error calling guard method= " + method, exc);
        }
    }

    private Method findMethod(Object pTarget, String pMethodName) {

        final Method[] methods = pTarget.getClass().getMethods();
        for (Method met : methods) {
            if (met.getName().equals(pMethodName)) {
                if (!met.getReturnType().equals(Boolean.class) &&
                    !met.getReturnType().equals(boolean.class)) {
                    throw new RuntimeException("Wrong return type for method '" + pMethodName + "'." +
                            " Expected Boolean/boolean, but was: '" + met.getReturnType() + "'");                    
                }
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
                if (annotation.annotationType().equals(GuardMethod.class)) {
                    return met;
                }                
            }
            
        }
        throw new RuntimeException("No guard method annotations found on class: " + pTarget);
    }

}
