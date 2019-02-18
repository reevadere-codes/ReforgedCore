package com.conquestreforged.core.block.props;

import com.conquestreforged.core.block.annotation.Name;
import com.conquestreforged.core.util.Cache;
import net.minecraft.block.Block;

import java.lang.annotation.Annotation;

public class NameCache extends Cache<Class<? extends Block>, Name> {

    private static final NameCache instance = new NameCache();

    private NameCache() {}

    @Override
    public Name compute(Class<? extends Block> type) {
        Name name = type.getAnnotation(Name.class);
        if (name == null) {
            return DEFAULT;
        }
        return name;
    }

    private static Name DEFAULT = new Name() {

        @Override
        public Class<? extends Annotation> annotationType() {
            return Name.class;
        }

        @Override
        public String value() {
            return "%s";
        }

        @Override
        public boolean plural() {
            return true;
        }
    };

    public static NameCache getInstance() {
        return instance;
    }
}
