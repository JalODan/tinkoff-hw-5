package kz.oj.tinkoffhw5.web.rest.v1.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class LocationDto {

    private UUID id;
    private String slug;
    private String name;
}
