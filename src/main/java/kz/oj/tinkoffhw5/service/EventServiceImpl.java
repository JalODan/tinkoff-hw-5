package kz.oj.tinkoffhw5.service;

import jakarta.annotation.Nullable;
import kz.oj.tinkoffhw5.entity.Event;
import kz.oj.tinkoffhw5.exception.EntityNotFoundException;
import kz.oj.tinkoffhw5.exception.RelatedEntityNotFoundException;
import kz.oj.tinkoffhw5.mapper.EventMapper;
import kz.oj.tinkoffhw5.repository.EventRepository;
import kz.oj.tinkoffhw5.repository.PlaceRepository;
import kz.oj.tinkoffhw5.specification.EventSpecification;
import kz.oj.tinkoffhw5.web.rest.v1.dto.EventDto;
import kz.oj.tinkoffhw5.web.rest.v1.request.EventCreateRequest;
import kz.oj.tinkoffhw5.web.rest.v1.request.EventUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {

    private final PlaceRepository placeRepository;

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Override
    public List<EventDto> findAll(@Nullable String nameSubstring, @Nullable UUID placeId, @Nullable LocalDate fromDate, @Nullable LocalDate toDate) {

        Specification<Event> specification = Specification.where(
                EventSpecification.hasNameLike(nameSubstring)
                        .and(EventSpecification.isInPlaceWithId(placeId))
                        .and(EventSpecification.isAfter(fromDate))
                        .and(EventSpecification.isBefore(toDate))
        );

        return eventRepository.findAll(specification).stream()
                .map(eventMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventDto findById(Long id) {

        Event event = eventRepository.findEventWithPlaceById(id)
                .orElseThrow(EntityNotFoundException::new);

        return eventMapper.toDto(event);
    }

    @Override
    public EventDto create(EventCreateRequest request) {

        Event event = new Event();
        event.setName(request.name());
        event.setDate(request.date());
        event.setPlace(placeRepository.findById(request.placeId()).orElseThrow(RelatedEntityNotFoundException::new));

        Event saved = eventRepository.save(event);

        return eventMapper.toDto(saved);
    }

    @Override
    public EventDto update(Long id, EventUpdateRequest request) {

        Event event = eventRepository.findEventWithPlaceById(id)
                .orElseThrow(EntityNotFoundException::new);

        if (request.name() != null) {
            event.setName(request.name());
        }

        if (request.date() != null) {
            event.setDate(request.date());
        }

        if (request.placeId() != null) {
            event.setPlace(placeRepository.findPLaceWithEventsById(request.placeId()).orElseThrow(RelatedEntityNotFoundException::new));
        }

        Event saved = eventRepository.save(event);

        return eventMapper.toDto(saved);
    }

    @Override
    public void delete(Long id) {

        if (eventRepository.existsById(id)) {
            throw new EntityNotFoundException();
        }

        eventRepository.deleteById(id);
    }

}
