package kz.oj.tinkoffhw5.web.rest.v1.dto;

import lombok.Data;

@Data
public class CategoryDto {

    private Long id;
    private String slug;
    private String name;
}
