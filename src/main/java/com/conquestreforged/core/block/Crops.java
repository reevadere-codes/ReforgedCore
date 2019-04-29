package com.conquestreforged.core.block;

import com.conquestreforged.core.block.props.Props;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReaderBase;

public class Crops extends BlockCrops {

    private final IItemProvider seeds;
    private final IItemProvider crop;

    public Crops(Props props) {
        super(props.toProperties());
        this.seeds = props.get("seeds", IItemProvider.class);
        this.crop = props.get("crop", IItemProvider.class);
    }

    @Override
    public VoxelShape getRenderShape(IBlockState state, IBlockReader reader, BlockPos pos) {
        return VoxelShapes.fullCube();
    }

    @Override
    public boolean isValidPosition(IBlockState state, IWorldReaderBase reader, BlockPos pos) {
        return true;
    }

    @Override
    protected boolean isValidGround(IBlockState p_200014_1_, IBlockReader p_200014_2_, BlockPos p_200014_3_) {
        return true;
    }

    @Override
    protected IItemProvider getSeedsItem() {
        return seeds;
    }

    @Override
    protected IItemProvider getCropsItem() {
        return crop;
    }
}
