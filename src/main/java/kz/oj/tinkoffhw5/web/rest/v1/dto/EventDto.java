package kz.oj.tinkoffhw5.web.rest.v1.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EventDto {

    private Long id;
    private String name;
    private LocalDate date;
    private PlaceDto place;
}
