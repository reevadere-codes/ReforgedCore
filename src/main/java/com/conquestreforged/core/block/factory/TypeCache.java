package com.conquestreforged.core.block.factory;

import com.conquestreforged.core.util.Cache;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

import java.lang.reflect.Constructor;

public class TypeCache extends Cache<Class<? extends Block>, Type<?>> {

    private static final TypeCache instance = new TypeCache();

    private TypeCache() {}

    @Override
    public Type<?> compute(Class<? extends Block> type) {
        try {
            Constructor<? extends Block> constructor = type.getConstructor(IBlockState.class, Block.Properties.class);
            constructor.setAccessible(true);
            return new ChildConstructor<>(constructor);
        } catch (Throwable ignored) {

        }

        try {
            Constructor<? extends Block> constructor = type.getConstructor(Block.Properties.class);
            constructor.setAccessible(true);
            return new BaseConstructor<>(constructor);
        } catch (Throwable t) {
            throw new InitializationException(t);
        }
    }

    private static class BaseConstructor<T extends Block> implements Type.Base<T> {

        private final Constructor<T> constructor;

        private BaseConstructor(Constructor<T> constructor) {
            this.constructor = constructor;
        }

        @Override
        public T apply(Block.Properties builder) {
            try {
                return constructor.newInstance(builder);
            } catch (Throwable t) {
                throw new InitializationException(t);
            }
        }
    }

    private static class ChildConstructor<T extends Block> implements Type.Child<T> {

        private final Constructor<T> constructor;

        private ChildConstructor(Constructor<T> constructor) {
            this.constructor = constructor;
        }

        @Override
        public T apply(IBlockState state, Block.Properties builder) {
            try {
                return constructor.newInstance(state, builder);
            } catch (Throwable t) {
                throw new InitializationException(t);
            }
        }
    }

    public static TypeCache getInstance() {
        return instance;
    }
}
