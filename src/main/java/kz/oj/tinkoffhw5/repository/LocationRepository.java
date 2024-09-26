package kz.oj.tinkoffhw5.repository;

import kz.oj.tinkoffhw5.entity.Location;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class LocationRepository extends MapRepository<Location, UUID> {


}
