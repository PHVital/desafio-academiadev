package main.java.br.com.academiadev.infrastructure.persistence;

import main.java.br.com.academiadev.application.repositories.UserRepository;
import main.java.br.com.academiadev.domain.entities.User;

import java.util.*;

public class UserRepositoryInMemory implements UserRepository {
    private final Map<String, User> db = new HashMap<>();

    @Override
    public void save(User user) {
        db.put(user.getEmail(), user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(db.get(email));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(db.values());
    }

    @Override
    public boolean existsByEmail(String email) {
        return db.containsKey(email);
    }
}
