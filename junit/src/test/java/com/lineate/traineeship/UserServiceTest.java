package com.lineate.traineeship;

import static org.junit.jupiter.api.Assertions.assertAll;
import org.junit.Test;

import java.util.Collection;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class UserServiceTest {
    private UserService userService = ServiceFactory.createUserService();

    @Test(expected = Exception.class)
    public void testCreateUserWithEmptyNameWithoutGroup() {
        userService.createUser("");
    }

    @Test(expected = Exception.class)
    public void testCreateUserWithEmptyNameWithGroup() {
        userService.createUser("", new Group("One"));
    }

    @Test(expected = Exception.class)
    public void testCreateUserWithEmptyNameGroup() {
        userService.createUser("Katya", new Group(""));
    }

    @Test(expected = Exception.class)
    public void testCreateUserWithNullNameWithoutGroup() {
        userService.createUser(null);
    }

    @Test(expected = Exception.class)
    public void testCreateUserWithNullNameWithGroup() {
        userService.createUser(null, new Group("One"));
    }

    @Test(expected = Exception.class)
    public void testCreateUserWithNullNameGroup() {
        userService.createUser("Katya", new Group(null));
    }

    @Test
    public void testCreateUserWithDefaultGroup() {
        User user = userService.createUser("Katya");
        Collection<Group> expected_groups = new HashSet<>();
        expected_groups.add(new Group("Katya"));

        assertAll(
                () -> assertEquals("Katya", user.getName()),
                () -> assertEquals(expected_groups, user.getGroups())
        );
    }

    @Test(expected = Exception.class)
    public void testCreateGroupWithNullName() {
        userService.createGroup(null);
    }

    @Test(expected = Exception.class)
    public void testCreateGroupWithEmptyName() {
        userService.createGroup("");
    }
}
