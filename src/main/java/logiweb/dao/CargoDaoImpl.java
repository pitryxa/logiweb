package logiweb.dao;

import logiweb.dao.api.CargoDao;
import logiweb.dao.generic.GenericDAOImpl;
import logiweb.entity.Cargo;
import logiweb.entity.Driver;
import logiweb.entity.Order;
import logiweb.entity.WaypointEntity;
import logiweb.entity.enums.CargoStatus;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CargoDaoImpl extends GenericDAOImpl<Cargo> implements CargoDao {
    @Override
    public List<Cargo> getAllSorted() {
        List<Cargo> list = entityManager.createQuery("select cargo from Cargo cargo order by cargo.id desc", Cargo.class)
                                        .getResultList();
        return list.isEmpty() ? new ArrayList<>() : list;
    }

    @Override
    public List<Cargo> getPreparedCargo() {
        List<Cargo> list = entityManager.createQuery(
                "select cargo from Cargo cargo where cargo.status = :status order by cargo.id", Cargo.class)
                                        .setParameter("status", CargoStatus.PREPARED)
                                        .getResultList();

        return list.isEmpty() ? new ArrayList<>() : list;
    }

    @Override
    public List<Cargo> getByListId(List<Integer> ids) {
        List<Cargo> list =
                entityManager.createQuery("select cargo from Cargo cargo where cargo.id in :ids", Cargo.class)
                             .setParameter("ids", ids)
                             .getResultList();

        return list.isEmpty() ? new ArrayList<>() : list;
    }

    @Override
    public Order getOrderByCargo(Cargo cargo) {
        List<WaypointEntity> waypointEntityList =
                entityManager.createQuery("select w from WaypointEntity w where w.cargo = :cargo", WaypointEntity.class)
                             .setParameter("cargo", cargo)
                             .getResultList();
        return waypointEntityList.isEmpty() ? null : waypointEntityList.get(0).getOrder();
    }

    @Override
    public Cargo getPreparedCargoById(Integer id) {
        Cargo cargo;
        try {
            cargo = entityManager.createQuery("select c from Cargo c where c.id = ?1 and c.status = 'PREPARED'", Cargo.class)
                                  .setParameter(1, id)
                                  .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        return cargo;
    }
}
