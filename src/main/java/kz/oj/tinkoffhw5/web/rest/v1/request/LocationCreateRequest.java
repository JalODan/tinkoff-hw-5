package kz.oj.tinkoffhw5.web.rest.v1.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LocationCreateRequest {

    private String slug;
    private String name;
}