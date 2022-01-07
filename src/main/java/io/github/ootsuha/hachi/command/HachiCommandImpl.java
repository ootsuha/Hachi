package io.github.ootsuha.hachi.command;

/**
 * Implements the methods in <code>HachiCommand</code>.
 */
public abstract class HachiCommandImpl implements HachiCommand {
    /**
     * Name of the command.
     */
    private String name;
    /**
     * Description of the command.
     */
    private String description;

    @Override public final String getName() {
        if (this.name == null) {
            return getClass().getSimpleName().toLowerCase();
        }
        return this.name;
    }

    /**
     * Sets <code>this.name</code>.
     *
     * @param name new value
     */
    protected void setName(final String name) {
        this.name = name;
    }

    @Override public final String getDescription() {
        return this.description;
    }

    /**
     * Sets <code>this.description</code>.
     *
     * @param description new value
     */
    protected void setDescription(final String description) {
        this.description = description;
    }

}
