package logiweb.validator;

import logiweb.dto.UserDto;
import logiweb.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class UserValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return UserDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserDto userDto = (UserDto) o;

        if (!userDto.getConfirmPassword().equals(userDto.getPassword())) {
            errors.rejectValue("confirmPassword", "Different.user.confirmPassword");
        }

        List<UserDto> userDtoList = userService.getAll();
        boolean isValid = true;
        for (UserDto user : userDtoList) {
            if (userDto.getEmail().equals(user.getEmail())) {
                isValid = false;
                break;
            }
        }
        if (!isValid) {
            errors.rejectValue("email", "Equals.user.email");
        }
    }
}
