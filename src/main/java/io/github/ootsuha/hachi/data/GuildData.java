package io.github.ootsuha.hachi.data;

import lombok.*;
import net.dv8tion.jda.api.entities.*;

import javax.persistence.*;
import java.util.*;

@Entity
@SuppressWarnings("VisibilityModifier")
@NoArgsConstructor
public final class GuildData {
    @Id
    @Column(name = "id", nullable = false)
    public long id;
    @OneToMany(fetch = FetchType.EAGER)
    public Set<HachiRole> roles;

    public GuildData(final Guild guild) {
        this.id = guild.getIdLong();
        this.roles = new HashSet<>();
    }
}
