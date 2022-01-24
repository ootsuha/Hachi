package io.github.ootsuha.hachi.core.command.request;

/**
 * Holds the options that a user gives in a command request.
 */
public interface HachiCommandOptions {
    /**
     * Checks if an option with the specified name is given.
     *
     * @param optionName name
     * @return if the option is given
     */
    boolean hasOption(String optionName);

    /**
     * Gets the option with the specified name as a String.
     *
     * @param optionName name
     * @return string, null only if option is not required and is not present
     */
    String getString(String optionName);

    /**
     * Gets the option with the specified name as an Integer.
     *
     * @param optionName name
     * @return integer, null only if option is not required and is not present
     */
    Integer getInteger(String optionName);

    /**
     * Gets the option with the specified name as a Double.
     *
     * @param optionName name
     * @return double, null only if option is not required and is not present
     */
    Double getDouble(String optionName);

    /**
     * Gets the option with the specified name as a Boolean.
     *
     * @param optionName name
     * @return boolean, null only if option is not required and is not present
     */
    Boolean getBoolean(String optionName);
}
