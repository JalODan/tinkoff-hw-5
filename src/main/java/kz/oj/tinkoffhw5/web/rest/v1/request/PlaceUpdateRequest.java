package kz.oj.tinkoffhw5.web.rest.v1.request;

import lombok.Data;

@Data
public class PlaceUpdateRequest {

    private String slug;
    private String name;
}