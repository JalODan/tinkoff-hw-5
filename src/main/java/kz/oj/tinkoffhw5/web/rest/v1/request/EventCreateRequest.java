package kz.oj.tinkoffhw5.web.rest.v1.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record EventCreateRequest(
        @NotBlank @Size(max = 255) String name,
        @NotNull LocalDate date,
        @NotNull UUID placeId
) {

}
