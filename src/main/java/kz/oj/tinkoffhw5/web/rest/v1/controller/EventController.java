package kz.oj.tinkoffhw5.web.rest.v1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kz.oj.tinkoffhw5.service.EventService;
import kz.oj.tinkoffhw5.web.rest.v1.dto.EventDto;
import kz.oj.tinkoffhw5.web.rest.v1.request.EventCreateRequest;
import kz.oj.tinkoffhw5.web.rest.v1.request.EventUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/events")
@Tag(name = "События")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping
    @Operation(summary = "Вывести список")
    public List<EventDto> findAll(
            @RequestParam(value = "nameSubstring", required = false) String nameSubstring,
            @RequestParam(value = "placeId", required = false) UUID placeId,
            @RequestParam(value = "fromDate", required = false) LocalDate fromDate,
            @RequestParam(value = "toDate", required = false) LocalDate toDate
    ) {
        return eventService.findAll(nameSubstring, placeId, fromDate, toDate);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Найти по ID")
    public EventDto findById(@PathVariable("id") Long id) {

        return eventService.findById(id);
    }

    @PostMapping
    @Operation(summary = "Создать")
    public EventDto create(@RequestBody @Valid EventCreateRequest request) {

        return eventService.create(request);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Обновить")
    public EventDto update(
            @PathVariable("id") Long id,
            @RequestBody @Valid EventUpdateRequest request
    ) {

        return eventService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить")
    public void delete(@PathVariable("id") Long id) {

        eventService.delete(id);
    }
}
