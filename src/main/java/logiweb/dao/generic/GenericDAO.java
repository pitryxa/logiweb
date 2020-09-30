package logiweb.dao.generic;

import java.util.List;

public interface GenericDAO<Entity> {
    void create(Entity entity);

    void update(Entity entity);

    Entity getById(Integer id);

    List<Entity> getAll();

    void delete(Entity entity);

}
