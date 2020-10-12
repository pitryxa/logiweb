package logiweb.dao;

import logiweb.dao.api.CargoDao;
import logiweb.dao.generic.GenericDAOImpl;
import logiweb.entity.Cargo;
import logiweb.entity.enums.CargoStatus;
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

    @Override
    public List<Cargo> getPreparedCargo() {
        List<Cargo> list = entityManager.createQuery("from Cargo cargo where cargo.status = :status order by cargo.id", Cargo.class)
                .setParameter("status", CargoStatus.PREPARED)
                .getResultList();

        return list.isEmpty() ? new ArrayList<>() : list;
    }

    @Override
    public List<Cargo> getByListId(List<Integer> ids) {
        List<Cargo> list = entityManager.createQuery("from Cargo cargo where cargo.id in :ids", Cargo.class)
                .setParameter("ids", ids)
                .getResultList();

        return list.isEmpty() ? new ArrayList<>() : list;
    }
}
