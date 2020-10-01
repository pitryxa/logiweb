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
    public List<City> getAllSorted() {
        //List<City> list = entityManager.createNativeQuery("SELECT * FROM city ORDER BY name", City.class).getResultList();

        List<City> list = entityManager.createQuery("FROM City city ORDER BY city.name", City.class).getResultList();
        return list.isEmpty() ? new ArrayList<>() : list;
    }

    @Override
    public City getByName(String name) {
        List<City> list = entityManager.createQuery("SELECT e FROM City e WHERE e.name = :name").setParameter("name", name).getResultList();
        return list.isEmpty() ? null : list.get(0);
    }


}
