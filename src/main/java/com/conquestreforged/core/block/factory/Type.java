package com.conquestreforged.core.block.factory;

import com.conquestreforged.core.block.props.Props;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

public interface Type<T extends Block> {

    T create(Props props) throws InitializationException;

    default Type<T> cast() {
        return this;
    }

    interface Base<T extends Block> extends Type<T> {

        T apply(Block.Properties builder);

        @Override
        default T create(Props props) throws InitializationException {
            Block.Properties builder = props.toProperties();
            return apply(builder);
        }
    }

    interface Child<T extends Block> extends Type<T> {

        T apply(IBlockState state, Block.Properties builder);

        @Override
        default T create(Props props) throws InitializationException {
            IBlockState parent = props.getParent();
            Block.Properties builder = props.toProperties();
            return apply(parent, builder);
        }
    }
}
