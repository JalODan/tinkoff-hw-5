package kz.oj.tinkoffhw5.exception;

import java.text.MessageFormat;

public class RelatedEntityNotFoundException extends RuntimeException{

    public RelatedEntityNotFoundException(Class<?> entityClass, Object id) {

        super(MessageFormat.format("Сущность \"{1}\" не найдена по id={2}", entityClass.getSimpleName(), id));
    }

}
