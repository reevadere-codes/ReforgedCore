package com.conquestreforged.core.block.annotation;

import java.lang.annotation.*;

@Repeatable(Models.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Model {

    String model();

    String name();

    boolean plural() default false;
}
