package logiweb.dao;

import logiweb.dao.api.DistanceDao;
import logiweb.dao.generic.GenericDAOImpl;
import logiweb.entity.Distance;
import org.springframework.stereotype.Repository;

@Repository
public class DistanceDaoImpl extends GenericDAOImpl<Distance> implements DistanceDao {

}
