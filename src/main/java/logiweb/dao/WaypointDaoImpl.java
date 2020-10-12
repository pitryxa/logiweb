package logiweb.dao;

import logiweb.dao.api.WaypointDao;
import logiweb.dao.generic.GenericDAOImpl;
import logiweb.entity.WaypointEntity;
import org.springframework.stereotype.Repository;

@Repository
public class WaypointDaoImpl extends GenericDAOImpl<WaypointEntity> implements WaypointDao {


}
