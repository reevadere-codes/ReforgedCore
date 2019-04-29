package com.conquestreforged.core.block;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class Soulsand extends Block {

    protected static final VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D);

    public Soulsand(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getCollisionShape(IBlockState state, IBlockReader reader, BlockPos pos) {
        return SHAPE;
    }

    @Override
    public void onEntityCollision(IBlockState state, World p_196262_2_, BlockPos pos, Entity player) {
        player.motionX *= 0.4D;
        player.motionZ *= 0.4D;
    }
}
