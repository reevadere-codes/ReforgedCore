package com.conquestreforged.core.block;

import com.conquestreforged.core.block.props.Props;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class Leaves extends BlockLeaves {

    private final IItemProvider sapling;

    public Leaves(Props props) {
        super(props.toProperties());
        this.sapling = props.get("sapling", IItemProvider.class);
    }

    @Override
    protected void dropApple(World world, BlockPos pos, BlockState state, int num) {
    }

    @Override
    public IItemProvider getItemDropped(BlockState p_199769_1_, World p_199769_2_, BlockPos p_199769_3_, int p_199769_4_) {
        return sapling;
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public boolean isLadder(BlockState state, IWorldReader world, BlockPos pos, EntityLivingBase entity) {
        return true;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return VoxelShapes.empty();
    }
}
