package logiweb.dao.api;

import logiweb.dao.generic.GenericDAO;
import logiweb.entity.User;
import logiweb.entity.enums.Role;

import java.util.List;

public interface UserDao extends GenericDAO<User> {
    List<User> getAllSorted();

    User getByEmail(String email);

    List<User> getByRole(Role role);

    List<User> getUsersWithRoleDriverWhoAreNotInListDrivers();

    Integer getUserIdByEmail(String currentUserName);
}
