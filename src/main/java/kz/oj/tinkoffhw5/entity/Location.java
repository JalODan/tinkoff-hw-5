package kz.oj.tinkoffhw5.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Location implements Identifiable<String> {

    private String id;
    private String slug;
    private String name;
}
