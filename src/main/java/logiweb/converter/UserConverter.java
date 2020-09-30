package logiweb.converter;

import logiweb.dto.CityDto;
import logiweb.dto.UserDto;
import logiweb.entity.City;
import logiweb.entity.User;
import logiweb.entity.enums.Role;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    public void toDto(User user, UserDto userDto) {
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setRole(user.getRole());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
    }

    public UserDto toDto(User user) {
        UserDto userDto = new UserDto();

        this.toDto(user, userDto);

        return userDto;
    }

    public void toEntity(User user, UserDto userDto) {
        user.setId(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setRole(userDto.getRole());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
    }

    public User toEntity(UserDto userDto) {
        User user = new User();

        this.toEntity(user, userDto);

        return user;
    }
}
