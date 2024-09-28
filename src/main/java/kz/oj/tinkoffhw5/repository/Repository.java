package kz.oj.tinkoffhw5.repository;

import kz.oj.tinkoffhw5.entity.Identifiable;

import java.util.List;
import java.util.Optional;

public interface Repository<ENTITY extends Identifiable<ID>, ID> {

    List<ENTITY> findAll();

    Optional<ENTITY> findById(ID id);

    ENTITY save(ENTITY entity);

    void deleteById(ID id);

    boolean existsById(ID id);
}
