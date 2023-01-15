package service;


import com.coldlight.restapicrudapp.entity.UserEntity;
import com.coldlight.restapicrudapp.repository.hibernate.HibernateUserRepositoryImpl;
import com.coldlight.restapicrudapp.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private HibernateUserRepositoryImpl hibernateUserRepository;

    @InjectMocks
    private UserService userService;

    @Captor
    ArgumentCaptor<UserEntity> userEntityArgumentCaptor;

    @Test
    public void createUserTest() {
        //given
        String firstName = "Igor";
        String lastName = "Popovich";
        UserEntity user = new UserEntity();
        user.setFirstName(firstName);
        user.setLastName(lastName);

        //when
        when(hibernateUserRepository.save(user)).thenReturn(user);

        //then
        assertEquals(user, userService.createUser(firstName, lastName));

    }

    @Test(expected = RuntimeException.class)
    public void createUserExceptionTest() {
        //given
        String firstName = "Igor";
        String lastName = "Popovich";
        UserEntity user = new UserEntity();
        user.setFirstName(firstName);
        user.setLastName(lastName);

        //when
        when(hibernateUserRepository.save(user)).thenThrow(new RuntimeException());

        //then
        userService.createUser(firstName, lastName);
    }

    @Test
    public void getAllUsersEmptyTest() {
        //when
        when(hibernateUserRepository.getAll()).thenReturn(new ArrayList<>());

        //then
        assertTrue(userService.getAllUsers().isEmpty());

    }

    @Test
    public void getAllUsersTest() {
        //given
        String firstName = "Igor";
        String lastName = "Popovich";
        UserEntity user = UserEntity.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build();


        String firstName2 = "Bob";
        String lastName2 = "Bobovich";
        UserEntity user2 = UserEntity.builder()
                .firstName(firstName2)
                .lastName(lastName2)
                .build();

        //when
        when(hibernateUserRepository.getAll()).thenReturn(List.of(user, user2));

        //then
        List<UserEntity> allUsers = userService.getAllUsers();
        assertEquals(2, allUsers.size());

        UserEntity firstUser = allUsers.get(0);
        assertEquals(firstName, firstUser.getFirstName());
        assertEquals(lastName, firstUser.getLastName());

        UserEntity secondUser = allUsers.get(1);
        assertEquals(firstName2, secondUser.getFirstName());
        assertEquals(lastName2, secondUser.getLastName());
    }

    @Test(expected = RuntimeException.class)
    public void getUsersExceptionTest() {


        //when
        when(hibernateUserRepository.getAll()).thenThrow(new RuntimeException());

        //then
        userService.getAllUsers();

    }

    @Test
    public void getUserByIDTest() {
        //given
        String firstName = "Igor";
        String lastName = "Popovich";
        UserEntity user = UserEntity.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build();
        Long id = 1L;

        //when
        when(hibernateUserRepository.getByID(id)).thenReturn(user);

        //then
        UserEntity userByID = userService.getUserByID(id);
        assertEquals(firstName, userByID.getFirstName());
        assertEquals(lastName, userByID.getLastName());

    }

    @Test(expected = RuntimeException.class)
    public void getUserByIDException() {
        //given
        Long id = 1L;

        //when
        when(hibernateUserRepository.getByID(id)).thenThrow(new RuntimeException());

        //then
        userService.getUserByID(id);
    }

    @Test(expected = RuntimeException.class)
    public void updateUserDataBaseExceptionTest() {

        //when
        when(hibernateUserRepository.getByID(any())).thenThrow(new RuntimeException());

        //then
        userService.updateUser(1L, null, null);
    }


    @Test(expected = RuntimeException.class)
    public void updateUserNotFoundTest() {
        //when
        when(hibernateUserRepository.getByID(any())).thenReturn(null);

        //then
        userService.updateUser(1L, null, null);
    }

    @Test
    public void updateUserTest() {
        String firstName = "Igor";
        String lastName = "Popovich";
        UserEntity user = UserEntity.builder()
                .id(1L)
                .firstName(firstName)
                .lastName(lastName)
                .build();

        //when
        when(hibernateUserRepository.getByID(1L)).thenReturn(user);
        when(hibernateUserRepository.update(userEntityArgumentCaptor.capture())).thenReturn(user);

        //then
        userService.updateUser(1L, "Bob", "Bobovich");
        assertEquals("Bob", userEntityArgumentCaptor.getValue().getFirstName());
        assertEquals("Bobovich", userEntityArgumentCaptor.getValue().getLastName());
        assertEquals(1L, userEntityArgumentCaptor.getValue().getId());
    }

    @Test(expected = RuntimeException.class)
    public void deleteUserNotFoundTest() {
        //when
        when(hibernateUserRepository.getByID(any())).thenReturn(null);

        //then
        userService.deleteUserByID(1L);
    }

    @Test
    public void deleteUserTest() {
        String firstName = "Igor";
        String lastName = "Popovich";
        UserEntity user = UserEntity.builder()
                .id(1L)
                .firstName(firstName)
                .lastName(lastName)
                .build();

        //when
        when(hibernateUserRepository.getByID(1L)).thenReturn(user);
        doNothing().when(hibernateUserRepository).delete(userEntityArgumentCaptor.capture());


        //then
        userService.deleteUserByID(1L);
        assertEquals(1L, userEntityArgumentCaptor.getValue().getId());
    }
}
