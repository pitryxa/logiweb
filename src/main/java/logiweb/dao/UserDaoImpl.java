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
        List<User> list = entityManager.createQuery("SELECT e From User e ORDER BY e.id").getResultList();
        return list.isEmpty() ? new ArrayList<>() : list;
    }

    @Override
    public User getByEmail(String email) {
        List<User> list = entityManager.createQuery("select e from User e where e.email = :email")
                .setParameter("email", email.toLowerCase())
                .getResultList();

        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<User> getByRole(Role role) {
        List<User> list = entityManager.createQuery("select e from User e where e.role = :role")
                .setParameter("role", role)
                .getResultList();

        return list.isEmpty() ? new ArrayList<>() : list;
    }
}
