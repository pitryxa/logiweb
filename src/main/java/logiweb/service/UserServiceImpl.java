package logiweb.service;

import logiweb.dao.api.DriverDao;
import logiweb.dao.api.UserDao;
import logiweb.dto.UserDto;
import logiweb.entity.User;
import logiweb.entity.enums.DriverStatus;
import logiweb.entity.enums.Role;
import logiweb.service.api.UserService;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private DriverDao driverDao;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public List<UserDto> getAll() {
        return userDao.getAllSorted()
                .stream()
                .map(user -> mapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void add(UserDto userDto) {
        userDto.setPassword(encoder.encode(userDto.getPassword()));
        userDto.setRole(Role.ROLE_NONE);
        userDao.create(mapper.map(userDto, User.class));
        logger.info("User is added.");
    }

    @Override
    @Transactional
    public void edit(UserDto userDto, Role currentRole) {
        userDto.setPassword(getById(userDto.getId()).getPassword());

        if (currentRole == Role.ROLE_DRIVER && userDto.getRole() != currentRole) {
            driverDao.disableByUserId(userDto.getId());
            logger.info("The driver is disabled.");
        }

        userDao.update(mapper.map(userDto, User.class));
        logger.info("User is updated.");
    }

    @Override
    public void edit(UserDto userDto) {
        userDto.setPassword(getById(userDto.getId()).getPassword());
        userDao.update(mapper.map(userDto, User.class));
        logger.info("User is updated.");
    }

    @Override
    public UserDto getById(int id) {
        return mapper.map(userDao.getById(id), UserDto.class);
    }

    @Override
    public UserDto getByEmail(String email) {
        User user = userDao.getByEmail(email);
        UserDto userDto = null;

        if (user != null) {
            logger.info(String.format("Successfully got user with login %s", user.getEmail()));
            userDto = mapper.map(user, UserDto.class);
        }

        return userDto;
    }

    @Override
    public List<UserDto> getByRole(Role role) {
        return userDao.getByRole(role)
                .stream()
                .map(user -> mapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getUsersWithRoleDriverWhoAreNotInListDrivers() {
        return userDao.getUsersWithRoleDriverWhoAreNotInListDrivers()
                .stream()
                .map(user -> mapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isUserBusyDriver(UserDto userDto) {
        if (userDto.getRole() != Role.ROLE_DRIVER) {
            return false;
        }

        if (!driverDao.isUserAssignToOrder(userDto.getId())) {
            return false;
        }

        return true;
    }
}
