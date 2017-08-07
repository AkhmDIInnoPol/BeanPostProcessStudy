package ru.spring.exper.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface SetSum {

    int val1() default 0;
    int val2() default 0;
}
