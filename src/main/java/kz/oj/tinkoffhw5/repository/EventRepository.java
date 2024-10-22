package kz.oj.tinkoffhw5.repository;

import kz.oj.tinkoffhw5.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {

    @Query("SELECT e FROM Event e JOIN FETCH e.place WHERE e.id = :id")
    Optional<Event> findEventWithPlaceById(@Param("id") Long id);
}
