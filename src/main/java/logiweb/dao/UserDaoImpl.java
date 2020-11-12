package logiweb.dao;

import logiweb.dao.api.UserDao;
import logiweb.dao.generic.GenericDAOImpl;
import logiweb.entity.User;
import logiweb.entity.enums.Role;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImpl extends GenericDAOImpl<User> implements UserDao {
    @Override
    public List<User> getAllSorted() {
        List<User> list =
                entityManager.createQuery("SELECT e From User e ORDER BY e.id desc", User.class).getResultList();
        return list.isEmpty() ? new ArrayList<>() : list;
    }

    @Override
    public User getByEmail(String email) {
        List<User> list = entityManager.createQuery("select e from User e where e.email = :email", User.class)
                                       .setParameter("email", email.toLowerCase())
                                       .getResultList();

        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<User> getByRole(Role role) {
        List<User> list = entityManager.createQuery("select e from User e where e.role = :role", User.class)
                                       .setParameter("role", role)
                                       .getResultList();

        return list.isEmpty() ? new ArrayList<>() : list;
    }

    @Override
    public List<User> getUsersWithRoleDriverWhoAreNotInListDrivers() {
        String query =
                "select distinct u.* from users u left join driver d " +
                "on u.id = d.user_id where u.role = 'ROLE_DRIVER' and d.id is null";

        List<User> userList = entityManager.createNativeQuery(query, User.class).getResultList();

        return userList.isEmpty() ? new ArrayList<>() : userList;
    }

    @Override
    public Integer getUserIdByEmail(String currentUserName) {
        List<User> list = entityManager.createQuery("select u from User u where u.email = :email", User.class)
                                       .setParameter("email", currentUserName)
                                       .getResultList();

        return list.isEmpty() ? null : list.get(0).getId();
    }
}
