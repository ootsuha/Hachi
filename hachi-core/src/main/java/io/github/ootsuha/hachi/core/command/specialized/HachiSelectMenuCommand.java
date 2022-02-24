package io.github.ootsuha.hachi.core.command.specialized;

import io.github.ootsuha.hachi.core.command.*;
import io.github.ootsuha.hachi.core.command.request.*;
import io.github.ootsuha.hachi.core.listener.*;
import io.github.ootsuha.hachi.core.types.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.interactions.components.*;
import net.dv8tion.jda.api.interactions.components.selections.*;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;

/**
 * A <code>HachiCommand</code> that replies with a select menu for embeds.
 */
public interface HachiSelectMenuCommand extends HachiCommand {
    /**
     * Schedules the removal of the select menu.
     */
    ScheduledExecutorService SCHEDULER = Executors.newSingleThreadScheduledExecutor();
    /**
     * Number of idle minutes until the select menu is removed.
     */
    int TIMEOUT_MINUTES = 10;

    /**
     * Returns the message embed for a certain selection.
     *
     * @param r         command request
     * @param selection select menu choice
     * @return message embed
     */
    MessageEmbed output(HachiCommandRequest r, String selection);

    /**
     * Returns the list of selections of the select menu.
     *
     * @param r command request
     * @return list of string
     */
    List<String> menuItems(HachiCommandRequest r);

    @Override
    default void run(HachiCommandRequest r) {
        var items = menuItems(r);
        // if only 0 or 1 selections, don't bother setting up select menu
        if (items.size() <= 1) {
            r.replyEmbed(output(r, items.get(0))).queue();
            return;
        }

        // create the select menu
        String id = UUID.randomUUID().toString();
        var b = SelectMenu.create(id);
        items.forEach(item -> b.addOption(item, item));
        var menu = b.build();

        // reply with the first embed
        var reply = r.replyEmbed(output(r, items.get(0))).setActionRow(ActionRow.of(menu)).complete();

        // supplies a scheduled future for the removal of the select menu
        Supplier<ScheduledFuture<?>> scheduleRemove = () -> SCHEDULER.schedule(() -> {
            SelectMenuListener.removeMenu(id);
            reply.removeComponents().queue();
        }, TIMEOUT_MINUTES, TimeUnit.MINUTES);

        // used to cancel the menu removal if an option is picked
        Mutable<ScheduledFuture<?>> future = Mutable.of(scheduleRemove.get());
        // add a callback to receive interactions
        SelectMenuListener.addMenu(id, event -> {
            // check if event is from the command requester
            if (event.getUser().getIdLong() != r.getUser().getIdLong()) {
                String response = String.format("You cannot use this select menu. (command originally called by %s)",
                        r.getUser().getAsMention());
                event.reply(response).setEphemeral(true).queue();
                return;
            }
            String selection = event.getInteraction().getValues().get(0);
            // change the embed based on the selection
            event.editMessageEmbeds(output(r, selection)).queue();
            // reset scheduled removal on interaction
            if (future.value().cancel(true)) {
                future.value(scheduleRemove.get());
            }
        });
    }
}
