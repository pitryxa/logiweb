package app.tests;

import logiweb.dao.api.DriverDao;
import logiweb.dao.api.UserDao;
import logiweb.entity.enums.Role;
import logiweb.service.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;

import static app.tests.DataInit.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @Mock
    private UserDao userDao;

    @Mock
    private DriverDao driverDao;

    @Mock
    private BCryptPasswordEncoder encoder;

    @InjectMocks
    private UserServiceImpl userService;

    @InjectMocks
    private ModelMapper mapper;

    @Before
    public void setUp() {
        DataInit.setUpAll();
        userService.setMapper(mapper);
    }

    @Test
    public void testCreateUser() {
        when(encoder.encode(userDtoNew.getPassword())).thenReturn(userDtoNew.getPassword());
        userService.add(userDtoNew);
        assertEquals(Role.ROLE_NONE, userDtoNew.getRole());
    }

    @Test
    public void testGetUserById() {
        when(userDao.getById(userNew.getId())).thenReturn(userNew);
        assertEquals(userService.getById(userNew.getId()).getId(), userDtoNew.getId());
    }

    @Test(expected = NullPointerException.class)
    public void testGetUserByIdNull() {
        when(userDao.getById(userNew.getId())).thenReturn(null);
        assertEquals(userService.getById(userNew.getId()).getId(), userDtoNew.getId());
    }

    @Test
    public void testGetUserByEmail() {
        when(userDao.getByEmail(userNew.getEmail())).thenReturn(userNew);
        assertEquals(userService.getByEmail(userNew.getEmail()).getId(), userDtoNew.getId());
    }

    @Test(expected = NullPointerException.class)
    public void testGetUserByEmailNull() {
        when(userDao.getByEmail(userNew.getEmail())).thenReturn(null);
        assertEquals(userService.getByEmail(userNew.getEmail()).getId(), userDtoNew.getId());
    }

    @Test
    public void testGetUsersByRole() {
        when(userDao.getByRole(userNew.getRole())).thenReturn(Arrays.asList(userNew));
        assertEquals(userService.getByRole(userNew.getRole()).size(), 1);
        assertEquals(userService.getByRole(userNew.getRole()).get(0).getId(), userDtoNew.getId());
    }

    @Test
    public void testGetUsersByRoleNull() {
        when(userDao.getByRole(userNew.getRole())).thenReturn(new ArrayList<>());
        assertEquals(userService.getByRole(userNew.getRole()).size(), 0);
    }

    @Test
    public void testIsUserBusyDriverFalse() {
        assertFalse(userService.isUserBusyDriver(userDtoNew));

        when(driverDao.isUserAssignToOrder(userDtoFreeDriver.getId())).thenReturn(false);
        assertFalse(userService.isUserBusyDriver(userDtoFreeDriver));
    }

    @Test
    public void testIsUserBusyDriverTrue() {
        when(driverDao.isUserAssignToOrder(userDtoFreeDriver.getId())).thenReturn(true);
        assertTrue(userService.isUserBusyDriver(userDtoFreeDriver));
    }



}
