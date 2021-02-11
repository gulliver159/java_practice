package com.lineate.traineeship;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertAll;


import static org.junit.Assert.assertEquals;

public class EntityServiceTest {

    private UserService userService = ServiceFactory.createUserService();
    private EntityService entityService = ServiceFactory.createEntityService();

    @Test
    public void testCheckMutabilityOfEntityValue() {
        Entity entity = new Entity("Entity", "123");
        entity.setValue("000");
        assertEquals("000", entity.getValue());
    }

    @Test
    public void testCheckAccessOnReadAndWriteToCreatorOfEntity() {
        User user = userService.createUser("Katya");
        Entity createdEntity = new Entity("Entity");
        entityService.save(user, createdEntity);

        Entity returnedEntity = entityService.getByName(user, createdEntity.getName());
        assertAll(
                () -> assertEquals(createdEntity, returnedEntity),
                () -> assertTrue(entityService.save(user, createdEntity)),
                () -> assertTrue(entityService.delete(user, createdEntity))
        );
    }

    @Test
    public void testCheckAccessOnWriteToCreatorMemberGroupWithoutAccess() {
        Group group = userService.createGroup("Group");
        User user = userService.createUser("Katya", group);

        Entity createdEntity = new Entity("Entity");
        entityService.save(user, createdEntity);

        entityService.grantPermission(createdEntity, group, Permission.read);

        Entity returnedEntity = entityService.getByName(user, createdEntity.getName());
        assertAll(
                () -> assertEquals(createdEntity, returnedEntity),
                () -> assertTrue(entityService.save(user, createdEntity)),
                () -> assertTrue(entityService.delete(user, createdEntity))
        );
    }

    @Test
    public void testCheckNotAccessForUserWithoutAccess() {
        User creator = userService.createUser("Katya");
        User user = userService.createUser("Nastya");

        Entity createdEntity = new Entity("Entity");
        entityService.save(creator, createdEntity);

        Entity returnedEntity = entityService.getByName(user, createdEntity.getName());
        assertAll(
                () -> assertNull(returnedEntity),
                () -> assertFalse(entityService.save(user, createdEntity)),
                () -> assertFalse(entityService.delete(user, createdEntity))
        );
    }

    @Test
    public void testCheckAccessOnReadForMembersGroupsIncludeCreator() {
        Group group1 = userService.createGroup("Group1");
        User creator = userService.createUser("Katya", group1);
        User user1 = userService.createUser("Nastya", group1);
        User user2 = userService.createUser("Boris", group1);

        Group group2 = userService.createGroup("Group2");
        User user3 = userService.createUser("Tanya", group2);
        User user4 = userService.createUser("Masha", group2);
        userService.addUserToGroup(creator, group2);

        Entity createdEntity = new Entity("Entity");
        entityService.save(creator, createdEntity);

        for (Group group : creator.getGroups()) {
            for (User user : group.getUsers()) {
                if (!user.equals(creator)) {
                    Entity returnedEntity = entityService.getByName(user, createdEntity.getName());
                    assertAll(
                            () -> assertEquals(createdEntity, returnedEntity),
                            () -> assertFalse(entityService.save(user, createdEntity)),
                            () -> assertFalse(entityService.delete(user, createdEntity))
                    );
                }
            }
        }
    }

    @Test
    public void testCheckNotAccessOnReadForMembersGroupsJoinCreatorAfterCreation() {
        Group group1 = userService.createGroup("Group1");
        User creator = userService.createUser("Katya", group1);
        User user1 = userService.createUser("Nastya", group1);
        User user2 = userService.createUser("Boris", group1);

        Entity createdEntity = new Entity("Entity");
        entityService.save(creator, createdEntity);

        Group group2 = userService.createGroup("Group2");
        User user3 = userService.createUser("Tanya", group2);
        User user4 = userService.createUser("Masha", group2);
        userService.addUserToGroup(creator, group2);

        for (Group group : creator.getGroups()) {
            for (User user : group.getUsers()) {
                if (!user.equals(creator)) {
                    Entity returnedEntity = entityService.getByName(user, createdEntity.getName());
                    assertAll(
                            () -> assertNull(returnedEntity),
                            () -> assertFalse(entityService.save(user, createdEntity)),
                            () -> assertFalse(entityService.delete(user, createdEntity))
                    );
                }
            }
        }
    }

    @Test
    public void testCheckAccessReadForGroupsAfterProvisionAccess() {
        User creator = userService.createUser("Katya");

        Entity createdEntity = new Entity("Entity");
        entityService.save(creator, createdEntity);

        Group group = userService.createGroup("Group");
        userService.createUser("Nastya", group);
        userService.createUser("Boris", group);

        entityService.grantPermission(createdEntity, group, Permission.read);

        for (User user : group.getUsers()) {
            Entity returnedEntity = entityService.getByName(user, createdEntity.getName());
            assertAll(
                    () -> assertEquals(createdEntity, returnedEntity),
                    () -> assertFalse(entityService.save(user, createdEntity)),
                    () -> assertFalse(entityService.delete(user, createdEntity))
            );
        }
    }

    @Test
    public void testCheckAccessWriteForGroupsAfterProvisionAccess() {
        User creator = userService.createUser("Katya");

        Entity createdEntity = new Entity("Entity");
        entityService.save(creator, createdEntity);

        Group group = userService.createGroup("Group");
        userService.createUser("Nastya", group);
        userService.createUser("Boris", group);

        entityService.grantPermission(createdEntity, group, Permission.write);

        for (User user : group.getUsers()) {
            Entity returnedEntity = entityService.getByName(user, createdEntity.getName());
            assertAll(
                    () -> assertEquals(createdEntity, returnedEntity),
                    () -> assertTrue(entityService.save(user, createdEntity))
            );
        }
    }

    @Test
    public void testCheckAccessReadAndWriteForGroupsAfterProvisionAccess() {
        User creator = userService.createUser("Katya");

        Entity createdEntity = new Entity("Entity");
        entityService.save(creator, createdEntity);

        Group group = userService.createGroup("Group");
        userService.createUser("Nastya", group);
        userService.createUser("Boris", group);

        entityService.grantPermission(createdEntity, group, Permission.read);
        entityService.grantPermission(createdEntity, group, Permission.write);

        for (User user : group.getUsers()) {
            Entity returnedEntity = entityService.getByName(user, createdEntity.getName());
            assertAll(
                    () -> assertEquals(createdEntity, returnedEntity),
                    () -> assertTrue(entityService.save(user, createdEntity))
            );
        }
    }


}
