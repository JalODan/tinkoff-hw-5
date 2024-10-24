package kz.oj.tinkoffhw5.service;


import jakarta.annotation.Nullable;
import kz.oj.tinkoffhw5.web.rest.v1.dto.EventDto;
import kz.oj.tinkoffhw5.web.rest.v1.request.EventCreateRequest;
import kz.oj.tinkoffhw5.web.rest.v1.request.EventUpdateRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface EventService {

    List<EventDto> findAll(
            @Nullable String nameSubstring,
            @Nullable UUID placeId,
            @Nullable LocalDate fromDate,
            @Nullable LocalDate toDate
    );

    EventDto findById(Long id);
    EventDto create(EventCreateRequest request);

    EventDto update(Long id, EventUpdateRequest request);

    void delete(Long id);
}
