package net.rewerk.servlets.repository;

import net.rewerk.servlets.enumerator.UserRole;
import net.rewerk.servlets.model.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.file.Paths;
import java.util.UUID;

public class UserRepositoryTest {
    UserRepository userRepository;

    @Before
    public void setUp() {
        try {
            flushRepositoryResource();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        userRepository = UserRepository.getInstance();
    }

    @After
    public void tearDown() {
        userRepository.deleteAll();
        userRepository = null;
    }

    @Test
    public void whenGetInstance_thenCorrectCount() {
        Assert.assertEquals(0, userRepository.count());
    }

    @Test
    public void givenUser_whenUserSave_thenCorrectCount() {
        userRepository.save(getUser());

        Assert.assertEquals(1, userRepository.count());
    }

    @Test
    public void givenUser_whenUserRemove_thenCorrectCount() {
        User user = getUser();
        userRepository.save(user);
        userRepository.deleteAll();

        Assert.assertEquals(0, userRepository.count());
    }

    private User getUser() {
        return new User(UUID.randomUUID(), "Test", "test", UserRole.USER);
    }

    public static void flushRepositoryResource() throws NoSuchFieldException, IllegalAccessException {
        Field instance = UserRepository.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
        URL path = UserRepository.class.getResource("/db/users.json");
        if (path != null) {
            File file = new File(Paths.get(path.getPath()).toString());
            if (file.exists() && file.canWrite()) {
                if (!file.delete()) {
                    throw new RuntimeException("Could not delete file " + file.getAbsolutePath());
                }
            }
        }
    }
}
