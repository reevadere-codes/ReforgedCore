package com.conquestreforged.core.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFourWay;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Fluids;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public class Table extends BlockFourWay {

    public Table(Properties properties) {
        super(0.0F, 0.0F, 16.0F, 16.0F, 16.0F, properties);
    }

    @Override
    public VoxelShape getCollisionShape(IBlockState state, IBlockReader worldIn, BlockPos pos) {
        return VoxelShapes.fullCube();
    }

    @Override
    public VoxelShape getShape(IBlockState state, IBlockReader worldIn, BlockPos pos) {
        return VoxelShapes.fullCube();
    }

    @Override
    public VoxelShape getRaytraceShape(IBlockState state, IBlockReader worldIn, BlockPos pos) {
        return VoxelShapes.fullCube();
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public IBlockState updatePostPlacement(IBlockState state, EnumFacing facing, IBlockState state1, IWorld world, BlockPos pos, BlockPos pos1) {
        if (state.get(WATERLOGGED)) {
            world.getPendingFluidTicks().scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return facing.getAxis().getPlane() == EnumFacing.Plane.HORIZONTAL ? state.with(FACING_TO_PROPERTY_MAP.get(facing), this.canTableConnectTo(world, pos, facing)) : super.updatePostPlacement(state, facing, state1, world, pos, pos1);
    }

    private boolean canTableConnectTo(IBlockReader reader, BlockPos pos, EnumFacing facing) {
        BlockPos offset = pos.offset(facing);
        IBlockState other = reader.getBlockState(offset);
        return other.canBeConnectedTo(reader, offset, facing.getOpposite()) || this.getDefaultState().canBeConnectedTo(reader, pos, facing);
    }

    @Override
    public boolean canBeConnectedTo(IBlockState state, IBlockReader reader, BlockPos pos, EnumFacing facing) {
        IBlockState other = reader.getBlockState(pos.offset(facing));
        return this.attachesTo(other);
    }

    private boolean attachesTo(IBlockState state) {
        Block block = state.getBlock();
        return block == this;
    }
}
