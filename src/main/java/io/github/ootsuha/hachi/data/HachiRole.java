package io.github.ootsuha.hachi.data;

import net.dv8tion.jda.api.entities.*;

import javax.persistence.*;

@Entity
@SuppressWarnings("VisibilityModifier")
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

    public HachiRole() {
    }

    public HachiRole(final Role role, final String type) {
        this.id = role.getIdLong();
        this.count = 0;
        this.type = type;
        this.name = role.getName();
        this.guildId = role.getGuild().getIdLong();
    }
}
