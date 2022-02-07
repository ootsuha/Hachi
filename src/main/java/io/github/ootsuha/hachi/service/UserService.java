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
interface UserRepository extends CrudRepository<UserData, Long> {
}

@Service
@SuppressWarnings("MissingJavadocMethod")
public final class UserService {
    private final UserRepository userRepository;
    private final Map<User, UserData> cache;

    @Autowired public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
        this.cache = new HashMap<>();
    }

    public UserData findByUser(final User user) {
        if (this.cache.containsKey(user)) {
            return this.cache.get(user);
        }
        var data = this.userRepository.findById(user.getIdLong()).orElseGet(() -> new UserData(user));
        this.cache.put(user, data);
        return data;
    }

    public UserData findByRequest(final HachiCommandRequest r) {
        return findByUser(r.getUser());
    }

    public void save(final UserData d) {
        this.userRepository.save(d);
    }
}
