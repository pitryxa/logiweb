package logiweb.dao;

import logiweb.dao.api.CargoDao;
import logiweb.dao.generic.GenericDAOImpl;
import logiweb.entity.Cargo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CargoDaoImpl extends GenericDAOImpl<Cargo> implements CargoDao {
    @Override
    public List<Cargo> getAllSorted() {
        List<Cargo> list = entityManager.createQuery("from Cargo cargo order by cargo.id", Cargo.class).getResultList();

//        entityManager.createNativeQuery("SELECT * FROM cargo ORDER BY id", Cargo.class).getResultList();

        return list.isEmpty() ? new ArrayList<>() : list;
    }
}
