package com.conquestreforged.core.block;

import net.minecraft.block.BlockPane;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;

public class LadderPane extends BlockPane {

    public LadderPane(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isLadder(BlockState state, IWorldReader reader, BlockPos pos, EntityLivingBase entity) {
        return true;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return VoxelShapes.empty();
    }
}
