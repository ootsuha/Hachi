package io.github.ootsuha.hachi.core.command.specialized;

import io.github.ootsuha.hachi.core.*;
import io.github.ootsuha.hachi.core.command.*;
import io.github.ootsuha.hachi.core.command.request.*;
import io.github.ootsuha.hachi.core.listener.*;
import io.github.ootsuha.hachi.core.types.*;
import net.dv8tion.jda.api.entities.*;

/**
 * A <code>HachiCommand</code> that replies with multiple embeds.
 */
public interface HachiPaginatedCommand extends HachiCommand {
    /**
     * Returns the number of pages.
     *
     * @param r command request
     * @return number of pages
     */
    int getPages(HachiCommandRequest r);

    /**
     * Returns the embed for a certain page.
     *
     * @param r command request
     * @param i page number
     * @return message embed
     */
    MessageEmbed output(HachiCommandRequest r, int i);

    @Override
    default void run(HachiCommandRequest r) {
        var row = new ButtonRow(2);
        row.setValues("back", "forward");
        row.setLabels(Emoji.fromUnicode("U+2B05"), Emoji.fromUnicode("U+27A1"));
        var page = Mutable.of(0);
        int maxPages = getPages(r);

        r.replyEmbed(output(r, page.value())).setActionRow(row.toRow()).complete();
        // add a callback to receive interactions
        ButtonListener.addRow(row, (event, value) -> {
            // check if event is from the command requester
            if (event.getUser().getIdLong() != r.getUser().getIdLong()) {
                String response = String.format("You cannot use this button. (command originally called by %s)",
                        r.getUser().getAsMention());
                event.reply(response).setEphemeral(true).queue();
                return;
            }
            if (value.equals("back")) {
                if (page.value() > 0) {
                    // decrement page value and update embed
                    page.value(page.value() - 1);
                    event.editMessageEmbeds(output(r, page.value())).queue();
                } else {
                    // acknowledge interaction, but don't change anything
                    event.deferEdit().queue();
                }
            } else if (value.equals("forward")) {
                if (page.value() < maxPages - 1) {
                    // increment page value and update embed
                    page.value(page.value() + 1);
                    event.editMessageEmbeds(output(r, page.value())).queue();
                } else {
                    // acknowledge interaction, but don't change anything
                    event.deferEdit().queue();
                }
            }
        });
    }
}
