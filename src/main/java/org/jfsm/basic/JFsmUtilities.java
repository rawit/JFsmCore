package org.jfsm.basic;

import org.apache.log4j.Logger;

/**
 * Utility class for JFsm. Contains methods to ease extraction of Java class/object information using the reflection API
 */
public final class JFsmUtilities {

    private static final int MAX_DOT_COUNT = 1000;

    private static final JFsmUtilities INSTANCE = new JFsmUtilities();

    private static final String DOT = ".";

    private static final Logger LOGGER = Logger.getLogger(JFsmUtilities.class);

    /**
     * Private constructor suppresses default public constructor.
     */
    private JFsmUtilities() {
    }

    /**
     * Gets the Instance attribute of the Utilities class.
     * 
     *@return The Instance value
     */
    public static JFsmUtilities getInstance() {
        return INSTANCE;
    }

    /**
     * Strip package and class name.
     * 
     *@param input String e.g "no.systek.jfsm.ClassName.MethodName"
     *@return The method name part "MethodName"
     */
    public static String getMethodName(final String input) {

        LOGGER.debug("input = " + input);

        if (input == null) {
            return null;
        }

        String output = input;

        int count = 0;
        while (output.indexOf(DOT) != -1) {
            if (count++ > 10) {
                break;
            }
            output = output.substring(output.indexOf(DOT) + 1);
        }

        LOGGER.debug("output = " + output);

        return output;
    }

    /**
     * Strip method name and return only class name with full package prefix For example "java.lang.String.toString"
     * will return "java.lang.String".
     * 
     *@param input The string to convert
     *@return The converted string
     */
    public static String getClassName(final String input) {

        LOGGER.debug("input = " + input);

        if (input == null) {
            return null;
        }

        final String output = input;

        int index = output.length() - 1;

        while (index > 0) {

            if (output.charAt(index) == '.') {
                LOGGER.debug("ouput = " + output.substring(0, index));
                return output.substring(0, index);
            }

            index--;

        }

        // No '.' found
        return output;
    }

    /**
     * Gets the "instanceIdentifier.methodName" part from a uniqueMethodString.
     * 
     *@param uniqueMethodString A string.
     *@return the string found, or null if a format error was found in the input string
     */
    public static String getMethodString(final String uniqueMethodString) {

        if (uniqueMethodString == null || uniqueMethodString.equals("")) {

            LOGGER.debug("uniqueMethodString is empty ");

            return uniqueMethodString;
        }

        final int index = uniqueMethodString.indexOf(":");

        if (index == -1) {
            LOGGER.debug("no ':' in uniqueMethodString = " + uniqueMethodString);

            return null;
        }

        final String method = uniqueMethodString.substring(0, index);

        // methods we don't wan't to show up
        if (method.equals("toString") || method.equals("equals")) {
            return null;
        }

        return method;
    }

    /**
     * Remove the package prefix from the string. I.e. "java.lang.String.toString" becomes "toString"
     * 
     *@param input The input string
     *@return The stripped string
     */
    public static String removePackagePrefix(final String input) {

        LOGGER.debug("Input input = " + input);

        if (input == null) {
            return null;
        }

        String output = input;

        int count = 0;
        while (output.indexOf(DOT) != -1) {
            if (count++ == MAX_DOT_COUNT) {
                break;
                // Just in case I made a fool of myself
            }
            output = output.substring(output.indexOf(DOT) + 1);
        }

        LOGGER.debug("Stripped method name = " + output);

        return output;
    }

    /**
     * Check the input string for variables, expressions that are surrounded by "${" and "}". Remove package prefix if
     * any. For example : "${Message = ${no.systek.jfsm.action.ReceiveSmsMsg.getMessage}, MSISDN =
     * ${no.systek.jfsm.action.ReceiveSmsMsg.getMsisdn}" should return : "${Message = ${ReceiveSmsMsg.getMessage},
     * MSISDN = ${ReceiveSmsMsg.getMsisdn}"
     * 
     *@param input The input string
     *@return The variable string, or null if none were found
     */
    public static String removePackagePrefixInVar(final String input) {

        LOGGER.debug("input = " + input);

        String variable = findVar(input);

        if (variable == null || variable.equals(input)) {
            // No matching curly braces found, return input
            return input;
        }

        // Remove the package prefix, if any
        variable = removePackagePrefix(getClassName(variable)) + DOT + getMethodName(variable);

        final String start = input.substring(0, input.indexOf("${") + 2);
        final String end = input.substring(input.indexOf("}"));

        return start + variable + removePackagePrefixInVar(end);
    }

    /**
     * Check the input string for variables, expression that are surrounded by "${" and "}". Return the enclosed string
     * of the first occurrence, or null if not found.
     * 
     *@param input The input string
     *@return The variable string, or null if none were found
     */
    public static String findVar(final String input) {

        LOGGER.debug("input = " + input);

        if (input == null) {
            return null;
        }

        final int startInd = input.indexOf("${");

        if (startInd == -1) {
            return input;
        }

        final String start = input.substring(startInd);
        LOGGER.debug("start string = " + start);

        final int endInd = start.indexOf("}");
        if (endInd == -1) {
            return input;
        }

        final String varStr = start.substring(2, endInd);
        LOGGER.debug("var string = " + varStr);
        return varStr;
    }

}
