package kz.oj.tinkoffhw5.web.rest.v1.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record EventUpdateRequest(
        @NotBlank @Size(max = 255) String name,
        LocalDate date,
        UUID placeId
) {

}
