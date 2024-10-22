package kz.oj.tinkoffhw5.repository;

import jdk.jfr.consumer.RecordedThread;
import kz.oj.tinkoffhw5.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryRepository extends MapRepository<Category, Long> {

    private static Long NEXT_ID = 1L;

    @Override
    public Category save(Category category) {

        if (category.getId() == null) {
            category.setId(nextId());
        }

        return super.save(category);
    }

    private Long nextId() {

        return NEXT_ID++;
    }
}
