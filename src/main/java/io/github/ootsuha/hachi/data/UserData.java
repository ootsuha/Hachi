package io.github.ootsuha.hachi.data;

import lombok.*;
import net.dv8tion.jda.api.entities.*;

import javax.persistence.*;
import java.util.*;

@Entity
@SuppressWarnings({ "VisibilityModifier", "MissingJavadocMethod" })
@NoArgsConstructor
public final class UserData {
    @Id
    @Column(name = "id", nullable = false)
    public long id;
    @ElementCollection(fetch = FetchType.EAGER)
    @Getter
    private Map<String, String> aliasMap;

    public UserData(final User u) {
        this.id = u.getIdLong();
        this.aliasMap = new HashMap<>();
    }
}
