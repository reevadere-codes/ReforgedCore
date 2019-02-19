package com.conquestreforged.core.block.annotation;

import java.lang.annotation.*;

@Repeatable(Models.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Model {

    String template();

    String value();

    boolean plural() default false;
}
