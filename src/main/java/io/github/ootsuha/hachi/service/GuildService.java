package io.github.ootsuha.hachi.service;

import io.github.ootsuha.hachi.core.command.request.*;
import io.github.ootsuha.hachi.data.*;
import net.dv8tion.jda.api.entities.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.repository.*;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
interface GuildRepository extends CrudRepository<GuildData, Long> {
}

@Service
@SuppressWarnings("MissingJavadocMethod")
public final class GuildService {
    private final GuildRepository guildRepository;
    private final Map<Guild, GuildData> cache;

    @Autowired
    public GuildService(final GuildRepository guildRepository) {
        this.guildRepository = guildRepository;
        this.cache = new HashMap<>();
    }

    public GuildData findByGuild(final Guild guild) {
        if (this.cache.containsKey(guild)) {
            return this.cache.get(guild);
        }
        var data = this.guildRepository.findById(guild.getIdLong()).orElseGet(() -> new GuildData(guild));
        this.cache.put(guild, data);
        return data;
    }

    public GuildData findByRequest(final HachiCommandRequest r) {
        return findByGuild(r.getChannel().getGuild());
    }

    public void save(final GuildData d) {
        this.guildRepository.save(d);
    }
}
