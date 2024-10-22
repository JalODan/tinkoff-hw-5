package kz.oj.tinkoffhw5.mapper;

import kz.oj.tinkoffhw5.entity.Location;
import kz.oj.tinkoffhw5.web.rest.v1.dto.LocationDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface LocationMapper {

    LocationDto toDto(Location location);
}
