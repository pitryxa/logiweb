package logiweb.service.api;

import logiweb.dto.UserDto;
import logiweb.entity.enums.Role;

import java.util.List;

public interface UserService {
    List<UserDto> getAll();

    void add(UserDto userDto);

    void edit(UserDto userDto, Role currentRole);

    UserDto getById(int id);

    UserDto getByEmail(String email);

    List<UserDto> getByRole(Role role);

    List<UserDto> getUsersWithRoleDriverWhoAreNotInListDrivers();

    boolean isUserBusyDriver(UserDto userDto);
}
