package io.github.ootsuha.hachi.data;

import lombok.*;
import net.dv8tion.jda.api.entities.*;

import javax.persistence.*;

@Entity
@SuppressWarnings("VisibilityModifier")
@NoArgsConstructor
public class HachiRole {
    @Id
    @Column(name = "id", nullable = false)
    public long id;
    @Column
    public int count;
    @Column
    public String type;
    @Column
    public String name;
    @Column
    public long guildId;

    public HachiRole(final Role role, final String type) {
        this.id = role.getIdLong();
        this.count = 0;
        this.type = type;
        this.name = role.getName();
        this.guildId = role.getGuild().getIdLong();
    }

    public HachiRole(final Role role, final String name, final String type) {
        this(role, type);
        this.name = name;
    }
}
