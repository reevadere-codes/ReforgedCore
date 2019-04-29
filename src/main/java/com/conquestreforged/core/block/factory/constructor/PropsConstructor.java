package com.conquestreforged.core.block.factory.constructor;

import com.conquestreforged.core.block.factory.BlockType;
import com.conquestreforged.core.block.factory.InitializationException;
import com.conquestreforged.core.block.props.Props;
import net.minecraft.block.Block;

import java.lang.reflect.Constructor;

public class PropsConstructor implements BlockType {

    private final Constructor<? extends Block> constructor;

    public PropsConstructor(Constructor<? extends Block> constructor) {
        this.constructor = constructor;
    }

    public Block newInstance(Object... args) throws InitializationException {
        try {
            return constructor.newInstance(args);
        } catch (Throwable t) {
            throw new InitializationException(t);
        }
    }

    @Override
    public Block create(Props props) throws InitializationException {
        return newInstance(props);
    }
}
