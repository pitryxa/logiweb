package logiweb.dao.generic;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class GenericDAOImpl<Entity> implements GenericDAO<Entity> {

    @PersistenceContext
    protected EntityManager entityManager;

    protected Class<Entity> daoClass;

    public GenericDAOImpl() {
        daoClass = (Class<Entity>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public void create(Entity entity) {
        entityManager.persist(entity);
    }

    @Override
    public void update(Entity entity) {
        entityManager.merge(entity);
    }

    @Override
    public void delete(Entity entity) {
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }

    @Override
    public Entity getById(Integer id) {
        return entityManager.find(daoClass, id);
    }

    @Override
    public List<Entity> getAll() {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Entity> criteriaQuery = criteriaBuilder.createQuery(daoClass);
        Root<Entity> root = criteriaQuery.from(daoClass);
        criteriaQuery.select(root);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }




}
