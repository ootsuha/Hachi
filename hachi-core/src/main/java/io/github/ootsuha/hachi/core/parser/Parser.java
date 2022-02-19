package io.github.ootsuha.hachi.core.parser;

import io.github.ootsuha.hachi.core.command.*;
import io.github.ootsuha.hachi.core.command.request.*;
import io.github.ootsuha.hachi.core.command.request.message.*;
import lombok.*;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.interactions.commands.build.*;

import javax.annotation.*;
import java.util.*;
import java.util.function.*;
import java.util.regex.*;

/**
 * Parses text into <code>HachiCommandRequest</code>s.
 */
@RequiredArgsConstructor
public final class Parser {
    public static final Pattern USER_MENTION_PATTERN = Pattern.compile("(?<=<@!)\\d+(?=>)");
    public static final Pattern ROLE_MENTION_PATTERN = Pattern.compile("(?<=<@&)\\d+(?=>)");
    private final HachiCommandLoader loader;
    private final String prefix;
    /**
     * Function that takes a message as an argument and returns a string to parse. Defaults to just returning <code>
     * message.getContentRaw().trim()</code>.
     */
    private final Function<Message, String> contentExtractor;
    @Setter
    private JDA jda;

    public Parser(final HachiCommandLoader loader, final String prefix) {
        this(loader, prefix, m -> m.getContentRaw().trim());
    }

    /**
     * Parse a message for a <code>HachiCommandRequest</code>.
     *
     * @param message message to be parsed
     * @return hachi command request, or null if invalid
     */
    @Nullable
    public HachiCommandRequest parse(final Message message) {
        String input = this.contentExtractor.apply(message);
        var result = parse(input, message.getGuild());
        if (result != null) {
            return new HachiMessageCommandRequest(message, result.left(), result.right());
        }
        return null;
    }

    /**
     * Parse a string.
     *
     * @param input string to parse
     * @param guild guild
     * @return immutable pair of hachi command and options, or null if invalid
     */
    @SuppressWarnings("checkstyle:MagicNumber")
    @Nullable
    public Pair<HachiCommand, HachiCommandOptions> parse(final String input, final Guild guild) {
        if (!input.startsWith(this.prefix)) {
            return null;
        }
        StringBuilder b = new StringBuilder(input);
        b.delete(0, this.prefix.length());
        String[] name = getToken(b).split("\\.");
        HachiCommand comm = switch (name.length) {
            case 1 -> this.loader.getCommand(name[0]);
            case 2 -> this.loader.getSubcommand(name[0], name[1]);
            case 3 -> this.loader.getSubcommand(name[0], name[1], name[2]);
            default -> null;
        };
        if (comm == null) {
            return null;
        }
        HachiCommandOptions options = parseOptions(b, comm.getCommandData(), guild);
        if (options == null) {
            return null;
        }
        return new Pair<>(comm, options);
    }

    /**
     * Parses options.
     *
     * @param b     input
     * @param data  command data
     * @param guild guild
     * @return hachi command option, or null if invalid options for the given command data
     */
    @Nullable
    private HachiCommandOptions parseOptions(final StringBuilder b, final CommandData data, final Guild guild) {
        return parseOptions(b, 0, new HashMap<>(), data, guild);
    }

    /**
     * Parses options recursively.
     *
     * @param b          string
     * @param ind        index to start at for list of option data
     * @param optionsMap map of current options
     * @param data       command data
     * @param guild      guild
     * @return hachi command option, or null if invalid options for the given command data
     */
    @Nullable
    private HachiCommandOptions parseOptions(final StringBuilder b, final int ind, final Map<String, Object> optionsMap,
            final CommandData data, final Guild guild) {
        List<OptionData> options = data.getOptions();
        for (int i = ind; i < options.size(); i++) {
            OptionData option = options.get(i);
            String token = getToken(b);
            boolean success = addOption(optionsMap, option, token, guild);
            if (!success) {
                // if option is not required, reset b and try to parse again by skipping current option
                if (!option.isRequired()) {
                    if (!b.isEmpty()) {
                        b.insert(0, ' ');
                    }
                    b.insert(0, token);
                    return parseOptions(b, i + 1, optionsMap, data, guild);
                }
                return null;
            }
        }
        if (!b.isEmpty()) {
            return null;
        }
        return new HachiCommandOptionsImpl(optionsMap, data);
    }

    /**
     * Adds an option to <code>options</code>.
     *
     * @param options    map to add option to
     * @param optionData option data for the option being added
     * @param s          token from message
     * @param guild      guild
     * @return whether the command succeeded or not
     */
    private boolean addOption(final Map<String, Object> options, final OptionData optionData, final String s,
            final Guild guild) {
        try {
            if (s.isBlank()) {
                return false;
            }
            Object o = switch (optionData.getType()) {
                case STRING -> s;
                case NUMBER -> Double.parseDouble(s);
                case BOOLEAN -> parseBoolean(s);
                case INTEGER -> Integer.parseInt(s);
                case USER -> parseUser(s, guild);
                case ROLE -> parseRole(s, guild);
                default -> null;
            };
            if (o == null) {
                return false;
            }
            options.put(optionData.getName(), o);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Gets the next token and removes it.
     *
     * @param b string builder
     * @return token
     */
    private String getToken(final StringBuilder b) {
        int i = b.indexOf(" ");
        if (i == -1) {
            i = b.length();
        }
        String s = b.substring(0, i).trim();
        b.delete(0, i + 1);
        return s;
    }

    /**
     * Parses a boolean from a string.
     *
     * @param s string
     * @return true if "true", false if "false", otherwise throw an exception
     */
    private boolean parseBoolean(final String s) {
        return switch (s.toLowerCase()) {
            case "true" -> true;
            case "false" -> false;
            default -> throw new NumberFormatException();
        };
    }

    /**
     * Parses a user from a string.
     *
     * @param s     string
     * @param guild guild to check in
     * @return user if found, otherwise throw exception
     */
    private User parseUser(final String s, final Guild guild) {
        String id = s;
        Matcher matcher = USER_MENTION_PATTERN.matcher(s);
        if (matcher.matches()) {
            id = matcher.group();
        }
        try {
            var member = guild.getMemberById(id);
            if (member != null) {
                return member.getUser();
            }
            member = guild.retrieveMemberById(id).complete();
            if (member != null) {
                return member.getUser();
            }
        } catch (Exception ignored) {
        }
        var user = this.jda.getUserByTag(s);
        if (user != null) {
            return user;
        }
        throw new NumberFormatException();
    }

    /**
     * Parses a string for a role.
     *
     * @param s     string
     * @param guild guild to check
     * @return role if found, otherwise throw exception
     */
    private Role parseRole(final String s, final Guild guild) {
        String id = s;
        Matcher matcher = ROLE_MENTION_PATTERN.matcher(s);
        if (matcher.matches()) {
            id = matcher.group();
        }
        try {
            var role = guild.getRoleById(id);
            if (role != null) {
                return role;
            }
        } catch (Exception ignored) {
        }
        var role = this.jda.getRolesByName(s, true);
        if (role.size() > 0) {
            return role.get(0);
        }
        throw new NumberFormatException();
    }
}
