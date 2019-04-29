package com.conquestreforged.core.block.factory.constructor;

import com.conquestreforged.core.block.factory.InitializationException;
import com.conquestreforged.core.block.props.Props;
import net.minecraft.block.Block;

import java.lang.reflect.Constructor;

public class StateConstructor extends PropsConstructor {
    public StateConstructor(Constructor<? extends Block> constructor) {
        super(constructor);
    }

    @Override
    public Block create(Props props) throws InitializationException {
        return newInstance(props.getParent(), props.toProperties());
    }
}
