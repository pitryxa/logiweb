package logiweb.dao;

import logiweb.dao.api.CityDao;
import logiweb.dao.generic.GenericDAOImpl;
import logiweb.entity.City;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CityDaoImpl extends GenericDAOImpl<City> implements CityDao {
    @Override
    public List<City> getAllSortedByName() {
        List<City> list = entityManager.createQuery("FROM City city ORDER BY city.name", City.class)
                                       .getResultList();
        return list.isEmpty() ? new ArrayList<>() : list;
    }

    @Override
    public List<City> getAllSortedById() {
        List<City> list = entityManager.createQuery("FROM City city ORDER BY city.id", City.class)
                                       .getResultList();
        return list.isEmpty() ? new ArrayList<>() : list;
    }

    @Override
    public City getByName(String name) {
        List<City> list = entityManager.createQuery("FROM City e WHERE e.name = :name", City.class)
                                       .setParameter("name", name)
                                       .getResultList();
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public Long countOfCities() {
        return (Long) entityManager.createQuery("select count(e) from City e").getSingleResult();
    }
}
