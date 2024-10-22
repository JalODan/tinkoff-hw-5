package kz.oj.tinkoffhw5.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Category implements Identifiable<Long> {

    private Long id;
    private String slug;
    private String name;
}
