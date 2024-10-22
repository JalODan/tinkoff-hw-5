package kz.oj.tinkoffhw5.entity;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Location implements Identifiable<UUID> {

    private UUID id;
    private String slug;
    private String name;
}
