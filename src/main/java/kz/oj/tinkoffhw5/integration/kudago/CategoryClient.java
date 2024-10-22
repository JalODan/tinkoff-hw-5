package kz.oj.tinkoffhw5.integration.kudago;

import kz.oj.tinkoffhw5.entity.Category;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "category", url = "${application.categories-url}")
public interface CategoryClient {

    @GetMapping
    List<Category> findAll();
}
