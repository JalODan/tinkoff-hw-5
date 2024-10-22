package kz.oj.tinkoffhw5.mapper;

import kz.oj.tinkoffhw5.entity.Event;
import kz.oj.tinkoffhw5.web.rest.v1.dto.EventDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR, uses = {PlaceMapper.class})
public interface EventMapper {

    EventDto toDto(Event event);
}
