package kz.oj.tinkoffhw5.repository;

import kz.oj.tinkoffhw5.entity.Identifiable;
import kz.oj.tinkoffhw5.entity.Location;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class MapRepository<ENTITY extends Identifiable<ID>, ID> implements Repository<ENTITY, ID> {

    private final Map<ID, ENTITY> map = new ConcurrentHashMap<>();

    @Override
    public List<ENTITY> findAll() {

        return map.values().stream().toList();
    }

    @Override
    public Optional<ENTITY> findById(ID id) {

        Objects.requireNonNull(id);

        if (!map.containsKey(id)) {
            return Optional.empty();
        }

        return Optional.of(map.get(id));
    }

    @Override
    public ENTITY save(ENTITY entity) {

        Objects.requireNonNull(entity);
        Objects.requireNonNull(entity.getId());

        map.put(entity.getId(), entity);

        return entity;
    }

    @Override
    public void deleteById(ID id) {

        map.remove(id);
    }

    @Override
    public boolean existsById(ID id) {
        return map.containsKey(id);
    }
}
