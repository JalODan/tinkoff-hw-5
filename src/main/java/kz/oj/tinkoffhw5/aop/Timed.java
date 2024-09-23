package kz.oj.tinkoffhw5.aop;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
public @interface Timed {

    String value() default "";
}
