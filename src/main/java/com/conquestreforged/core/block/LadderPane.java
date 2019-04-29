package com.conquestreforged.core.block;

import net.minecraft.block.BlockPane;
import net.minecraft.block.state.IBlockState;
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
    public boolean isLadder(IBlockState state, IWorldReader reader, BlockPos pos, EntityLivingBase entity) {
        return true;
    }

    @Override
    public VoxelShape getCollisionShape(IBlockState state, IBlockReader worldIn, BlockPos pos) {
        return VoxelShapes.empty();
    }
}
