package io.github.ootsuha.hachi.command;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

public abstract class HachiStringCommand extends HachiCommandImpl {
    protected abstract String output();
    @Override public final void run(final SlashCommandEvent e) {
        e.reply(output()).queue();
    }
}
