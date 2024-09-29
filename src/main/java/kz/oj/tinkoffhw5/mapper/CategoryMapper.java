package kz.oj.tinkoffhw5.mapper;

import kz.oj.tinkoffhw5.entity.Category;
import kz.oj.tinkoffhw5.web.rest.v1.dto.CategoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CategoryMapper {

    CategoryDto toDto(Category category);
}
