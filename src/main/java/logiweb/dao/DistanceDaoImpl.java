package logiweb.dao;

import logiweb.dao.api.DistanceDao;
import logiweb.dao.generic.GenericDAOImpl;
import logiweb.entity.City;
import logiweb.entity.Distance;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;

@Repository
public class DistanceDaoImpl extends GenericDAOImpl<Distance> implements DistanceDao {

    @Override
    public Distance get(String city1, String city2) {
        Distance distance;

        try {
            distance = entityManager.createQuery(
                    "select d from Distance d " +
                    "where (d.cityFrom.name = ?1 and d.cityTo.name = ?2) or (d.cityFrom.name = ?2 and d.cityTo.name = ?1)",
                    Distance.class).setParameter(1, city1).setParameter(2, city2).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

        return distance;
    }
}
