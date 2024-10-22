package kz.oj.tinkoffhw5.mapper;

import kz.oj.tinkoffhw5.entity.Place;
import kz.oj.tinkoffhw5.web.rest.v1.dto.PlaceDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface PlaceMapper {

    PlaceDto toDto(Place place);
}
