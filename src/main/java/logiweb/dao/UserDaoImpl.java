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
        List<User> list = entityManager.createQuery("SELECT e From User e ORDER BY e.id", User.class).getResultList();
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
        String query;

        query =
                "SELECT * FROM users u LEFT OUTER JOIN driver d ON u.id = d.user_id WHERE u.role = 'ROLE_DRIVER' AND d.id IS NULL";

        List<User> list = entityManager.createNativeQuery(query, User.class).getResultList();

        return list.isEmpty() ? new ArrayList<>() : list;
    }

    @Override
    public Integer getUserIdByEmail(String currentUserName) {
//        String query;
//
//        query = "select u from User u where u.email = :email";
//
//        List<User> list =
//                entityManager.createQuery(query, User.class).setParameter("email", currentUserName).getResultList();
//
//        return list.isEmpty() ? new ArrayList<>() : list.get(0);

        List<User> list = entityManager.createQuery("select u from User u where u.email = :email", User.class)
                                       .setParameter("email", currentUserName)
                                       .getResultList();

        return list.isEmpty() ? null : list.get(0).getId();


//        return entityManager.createNativeQuery("select id from users where email='" + currentUserName + "'")
//                            .getFirstResult();
    }
}
