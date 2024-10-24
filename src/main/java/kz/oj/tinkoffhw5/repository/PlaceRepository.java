package kz.oj.tinkoffhw5.repository;

import kz.oj.tinkoffhw5.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PlaceRepository extends JpaRepository<Place, UUID> {

    @Query("SELECT p FROM Place p LEFT JOIN FETCH p.events where p.id = :id")
    Optional<Place> findPLaceWithEventsById(@Param("id") UUID id);
}
