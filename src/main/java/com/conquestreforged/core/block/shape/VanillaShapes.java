package com.conquestreforged.core.block.shape;

import com.conquestreforged.core.util.State;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.IFluidState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class VanillaShapes {

    public static VoxelShape of(String state) {
        return of(State.parse(state));
    }

    public static VoxelShape of(BlockState state) {
        try {
            return state.getShape(dummyReader, BlockPos.ZERO);
        } catch (AbstractMethodError e) {
            return VoxelShapes.empty();
        }
    }

    private static final IBlockReader dummyReader = new IBlockReader() {
        @Nullable
        @Override
        public TileEntity getTileEntity(BlockPos pos) {
            throw new AbstractMethodError("unsupported");
        }

        @Override
        public BlockState getBlockState(BlockPos pos) {
            throw new AbstractMethodError("unsupported");
        }

        @Override
        public IFluidState getFluidState(BlockPos pos) {
            throw new AbstractMethodError("unsupported");
        }
    };
}
