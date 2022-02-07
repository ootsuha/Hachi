package io.github.ootsuha.hachi;

import io.github.ootsuha.hachi.core.*;
import io.github.ootsuha.hachi.core.command.*;
import io.github.ootsuha.hachi.core.parser.*;
import io.github.ootsuha.hachi.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.web.client.*;
import org.springframework.context.annotation.*;
import org.springframework.web.client.*;

import javax.security.auth.login.*;
import java.util.*;

/**
 * Main class.
 */
@SpringBootApplication
public class HachiApplication {
    /**
     * Main method.
     *
     * @param args command line args
     */
    public static void main(final String[] args) {
        SpringApplication.run(HachiApplication.class, args);
    }

    /**
     * Creates a Hachi instance and logs into Discord.
     *
     * @param config   hachi config
     * @param loader   hachi command loader
     * @param parser   parser
     * @param commands list of commands to add
     * @return hachi instance
     * @throws LoginException       invalid token in config
     * @throws InterruptedException method was interrupted
     */
    @Bean @Autowired public Hachi getHachi(final HachiConfig config, final HachiCommandLoader loader,
            final Parser parser, final List<HachiCommand> commands) throws LoginException, InterruptedException {
        for (HachiCommand command : commands) {
            command.setHelpEmbed(config);
            loader.loadCommand(command);
        }
        Hachi hachi = new Hachi(config, loader, parser);
        hachi.login();
        return hachi;
    }

    /**
     * Creates a new HachiCommandLoader.
     *
     * @return hachi command loader
     */
    @Bean public HachiCommandLoader getLoader() {
        return new HachiCommandLoader();
    }

    /**
     * Creates a Parser.
     *
     * @param loader      hachi command loader
     * @param config      hachi config
     * @param userService user service
     * @return parser
     */
    @Bean @Autowired public Parser getParser(final HachiCommandLoader loader, final HachiConfig config,
            final UserService userService) {
        return new Parser(loader, config.getPrefix(), e -> {
            String content = e.getContentRaw().trim();
            var data = userService.findByUser(e.getAuthor());
            for (var entry : data.getAliasMap().entrySet()) {
                String regex = String.format("((?<=^%s)%s)|(%%%<s%%)", config.getPrefix(), entry.getKey());
                content = content.replaceAll(regex, entry.getValue());
            }
            return content;
        });
    }

    @Bean public RestTemplate restTemplate(final RestTemplateBuilder builder) {
        return builder.build();
    }
}
