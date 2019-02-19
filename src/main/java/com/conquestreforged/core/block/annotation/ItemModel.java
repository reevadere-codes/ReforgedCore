package com.conquestreforged.core.block.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ItemModel {

    String value();

    String template() default "item/acacia_planks";

    boolean plural() default false;
}
