package io.github.ootsuha.hachi.commands;

import io.github.ootsuha.hachi.*;
import io.github.ootsuha.hachi.core.command.*;
import io.github.ootsuha.hachi.core.command.request.*;
import io.github.ootsuha.hachi.core.parser.*;
import net.dv8tion.jda.api.interactions.commands.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Component
public final class CallAs extends HachiCommandImpl {
    private final Parser parser;

    @Autowired
    public CallAs(final Parser parser) {
        super("call-as", "Call a command as another user.");
        addOption(OptionType.USER, "user", "User");
        addOption(OptionType.STRING, "command", "Command string.");
        this.parser = parser;
    }

    @Override
    public void run(final HachiCommandRequest r) {
        var o = r.getOptions();
        var pair = this.parser.parse(o.getString("command"), r.getChannel().getGuild());
        if (pair != null) {
            var user = o.getUser("user");
            new HachiRedirectedCommandRequest(r, user, pair.left(), pair.right()).fulfill();
        }
    }
}
