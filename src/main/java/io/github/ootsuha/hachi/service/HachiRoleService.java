package io.github.ootsuha.hachi.service;

import io.github.ootsuha.hachi.data.*;
import net.dv8tion.jda.api.entities.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.repository.*;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
interface HachiRoleRepository extends CrudRepository<HachiRole, Long> {
    Optional<HachiRole> findByGuildIdAndName(long guildId, String name);
}

@Service
@SuppressWarnings("MissingJavadocMethod")
public final class HachiRoleService {
    private final HachiRoleRepository hachiRoleRepository;
    private final Map<String, HachiRole> cache;

    @Autowired public HachiRoleService(final HachiRoleRepository hachiRoleRepository) {
        this.hachiRoleRepository = hachiRoleRepository;
        this.cache = new HashMap<>();
    }

    public HachiRole findByGuildAndName(final Guild g, final String name, final String type) {
        String key = g.getIdLong() + ',' + name;
        if (this.cache.containsKey(key)) {
            return this.cache.get(key);
        }
        var data = this.hachiRoleRepository.findByGuildIdAndName(g.getIdLong(), name).orElseGet(() -> {
            var r = g.createRole().setName(name).complete();
            return new HachiRole(r, type);
        });
        this.cache.put(key, data);
        return data;
    }

    public Optional<HachiRole> findByRole(final Role r) {
        return this.hachiRoleRepository.findById(r.getIdLong());
    }

    public void save(final HachiRole role) {
        this.hachiRoleRepository.save(role);
    }

    public void saveOrDeleteIfUnused(final HachiRole hachiRole, final Role role) {
        if (hachiRole.count <= 0) {
            this.hachiRoleRepository.deleteById(hachiRole.id);
            role.delete().queue();
        } else {
            this.hachiRoleRepository.save(hachiRole);
        }
    }

}
