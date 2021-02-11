package com.lineate.traineeship;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertFalse;

public class EntityServiceTestWithInvalidData {

    private UserService userService = ServiceFactory.createUserService();
    private EntityService entityService = ServiceFactory.createEntityService(Mockito.mock(EntityRepository.class));

    @Test
    public void testCreateEntityWithNullName() {
        User user = userService.createUser("Boris");
        assertFalse(entityService.save(user, Mockito.mock(Entity.class)));
    }

    @Test
    public void testCreateEntityWithNameWithSpaceSymbols() {
        User user = userService.createUser("Boris");
        assertFalse(entityService.save(user, new Entity("New Entity")));
    }

    @Test
    public void testCreateEntityWithTooLongName() {
        User user = userService.createUser("Boris");
        assertFalse(entityService.save(user, new Entity("123456789012345678901234567890123")));
    }

    @Test
    public void testCreateEntityWithEmptyName() {
        User user = userService.createUser("Boris");
        assertFalse(entityService.save(user, new Entity("")));
    }

}
