package logiweb.dao;

import logiweb.model.Cargo;
import logiweb.model.CargoStatus;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class CargoDaoImpl implements CargoDao {
    private static final AtomicInteger AUTO_ID = new AtomicInteger(0);
    private SessionFactory sessionFactory;
    private static Map<Integer, Cargo> cargoMap = new HashMap<>();
//    private static CargoDao cargoDao = null;

//    static {
//        Cargo cargo1 = new Cargo();
//        cargo1.setId(AUTO_ID.getAndIncrement());
//        cargo1.setName("Cargo 1");
//        cargo1.setWeight(100);
//        cargo1.setStatus(CargoStatus.PREPARED);
//        cargoMap.put(cargo1.getId(), cargo1);
//
//        Cargo cargo2 = new Cargo();
//        cargo2.setId(AUTO_ID.getAndIncrement());
//        cargo2.setName("Cargo 2");
//        cargo2.setWeight(150);
//        cargo2.setStatus(CargoStatus.SHIPPED);
//        cargoMap.put(cargo2.getId(), cargo2);
//
//        Cargo cargo3 = new Cargo();
//        cargo3.setId(AUTO_ID.getAndIncrement());
//        cargo3.setName("Cargo 3");
//        cargo3.setWeight(190);
//        cargo3.setStatus(CargoStatus.DELIVERED);
//        cargoMap.put(cargo3.getId(), cargo3);
//    }
//
////    private CargoDaoImpl() {
////    }
//
////    public static CargoDao instance() {
////        if (cargoDao == null) {
////            cargoDao = new CargoDaoImpl();
////        }
////        return cargoDao;
////    }

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Cargo> allCargo() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Cargo").list();

        //return new ArrayList<>(cargoMap.values());
    }

    @Override
    public void add(Cargo cargo) {
//        cargo.setId(AUTO_ID.getAndIncrement());
//        cargoMap.put(cargo.getId(), cargo);
        sessionFactory.getCurrentSession().persist(cargo);
    }

    @Override
    public void delete(Cargo cargo) {
//        cargoMap.remove(cargo.getId());
        sessionFactory.getCurrentSession().delete(cargo);
    }

    @Override
    public void edit(Cargo cargo) {
        //cargoMap.put(cargo.getId(), cargo);
        sessionFactory.getCurrentSession().update(cargo);
    }

    @Override
    public Cargo getById(int id) {
        return sessionFactory.getCurrentSession().get(Cargo.class, id);
    }
}
