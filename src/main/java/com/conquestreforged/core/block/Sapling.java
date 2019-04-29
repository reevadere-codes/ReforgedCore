package com.conquestreforged.core.block;

import com.conquestreforged.core.block.props.Props;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.trees.AbstractTree;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class Sapling extends BlockSapling {

    public Sapling(Props props) {
        super(props.get("tree", AbstractTree.class), props.toProperties());
    }

    @Override
    protected boolean isValidGround(IBlockState state, IBlockReader reader, BlockPos pos) {
        return true;
    }
}
