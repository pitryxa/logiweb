package logiweb.service;

import logiweb.converter.UserConverter;
import logiweb.dao.api.UserDao;
import logiweb.dto.CargoDto;
import logiweb.dto.UserDto;
import logiweb.entity.Cargo;
import logiweb.entity.User;
import logiweb.entity.enums.Role;
import logiweb.service.api.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    @Transactional
    public List<UserDto> getAll() {
        List<User> list = userDao.getAllSorted();
        return list.stream().map(user -> mapper.map(user, UserDto.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void add(UserDto userDto) {
        userDto.setPassword(encoder.encode(userDto.getPassword()));
        userDto.setRole(Role.ROLE_NONE);
        userDao.create(mapper.map(userDto, User.class));
    }

    @Override
    @Transactional
    public void delete(UserDto userDto) {

    }

    @Override
    @Transactional
    public void edit(UserDto userDto) {
        userDto.setPassword(getById(userDto.getId()).getPassword());
        //userDto.setConfirmPassword(encoder.encode(userDto.getPassword()));


        userDao.update(mapper.map(userDto, User.class));
    }

    @Override
    @Transactional
    public UserDto getById(int id) {
        User user = userDao.getById(id);
        return mapper.map(user, UserDto.class);
    }

    public List<UserDto> createUserDtoListFromUserList(List<User> userList) {
        List<UserDto> userDtoList = new ArrayList<>();

        for (User user : userList) {
            userDtoList.add(userConverter.toDto(user));
        }

        return userDtoList;
    }

    @Override
    @Transactional
    public UserDto getByEmail(String email) {
        User user = userDao.getByEmail(email);
        UserDto userDto = null;

        if (user != null) {
            userDto = mapper.map(user, UserDto.class);
        }

        return userDto;
    }

    @Override
    @Transactional
    public List<UserDto> getByRole(Role role) {
        return userDao.getByRole(role).stream().map(user -> mapper.map(user, UserDto.class)).collect(Collectors.toList());
    }
}
