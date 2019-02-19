package com.conquestreforged.core.block.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Assets {

    /**
     * @return the blockstate template for the annotated block
     */
    State state();

    /**
     * @return the item template for the annotated block
     */
    Model item();

    /**
     * @return the block model template(s) for the annotated block
     */
    Model[] block();
}
