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
@SuppressWarnings("checkstyle:DesignForExtension")
public class HachiApplication {
    public static void main(final String[] args) {
        SpringApplication.run(HachiApplication.class, args);
    }

     @Bean @Autowired public Hachi getHachi(final HachiConfig config, final HachiCommandLoader loader,
            final Parser parser, final List<HachiCommand> commands) throws LoginException, InterruptedException {
        for (HachiCommand command : commands) {
            command.setHelpEmbed(config);
            loader.loadCommand(command);
        }
        Hachi hachi = new Hachi(config, loader, parser);
        hachi.login();
        parser.setJda(hachi.getJda());
        return hachi;
    }

    @Bean public HachiCommandLoader getLoader() {
        return new HachiCommandLoader();
    }

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
