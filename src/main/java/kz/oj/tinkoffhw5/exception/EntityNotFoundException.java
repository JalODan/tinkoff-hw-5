package kz.oj.tinkoffhw5.exception;

import java.text.MessageFormat;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(Class<?> entityClass, Object id) {

        super(MessageFormat.format("Сущность \"{0}\" не найдена по id={1}", entityClass.getSimpleName(), id));
    }

}
