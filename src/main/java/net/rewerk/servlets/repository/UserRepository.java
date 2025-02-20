package net.rewerk.servlets.repository;

import net.rewerk.servlets.exception.SourceSaveException;
import net.rewerk.servlets.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserRepository extends AbstractRepository<User> {
    private final String FILENAME = "users.json";
    static UserRepository instance;
    private final List<User> users = new ArrayList<>();

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    private UserRepository() {
        try {
            users.addAll(super.getEntities(FILENAME, User.class));
        } catch (Exception e) {
            System.out.println(e.getMessage());
             throw new RuntimeException(e);
        }
    }

    public long count() {
        return users.size();
    }

    public boolean isExists(String username) {
        return users.stream().anyMatch(user -> user.getUsername().equals(username));
    }

    public User findByUsername(String username) {
        return users.stream().filter(user -> user.getUsername().equals(username)).findFirst().orElse(null);
    }

    public User findById(UUID id) {
        return users.stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null);
    }

    public void deleteAll() {
        this.users.clear();
    }

    public User save(User user) {
        this.users.add(user);
        try {
            super.save(this.users, FILENAME);
            return user;
        } catch (SourceSaveException e) {
            return null;
        }
    }
}
