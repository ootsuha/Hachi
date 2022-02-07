package io.github.ootsuha.hachi.data;

import net.dv8tion.jda.api.entities.*;

import javax.persistence.*;
import java.util.*;

@Entity
@SuppressWarnings({ "VisibilityModifier", "MissingJavadocMethod" })
public final class UserData {
    @Id
    @Column(name = "id", nullable = false)
    public long id;
    @ElementCollection(fetch = FetchType.EAGER)
    private Map<String, String> aliasMap;

    public UserData() {
    }

    public UserData(final User u) {
        this.id = u.getIdLong();
        this.aliasMap = new HashMap<>();
    }

    public Map<String, String> getAliasMap() {
        return this.aliasMap;
    }
}
