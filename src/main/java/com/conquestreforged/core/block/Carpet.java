package com.conquestreforged.core.block;

import com.conquestreforged.core.block.props.Props;
import net.minecraft.block.BlockCarpet;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReaderBase;

public class Carpet extends BlockCarpet {

    private final boolean floats;

    public Carpet(Props props) {
        super(props.dye(), props.toProperties());
        this.floats = props.floats();
    }

    @Override
    public boolean isValidPosition(IBlockState state, IWorldReaderBase worldIn, BlockPos pos) {
        return floats || super.isValidPosition(state, worldIn, pos);
    }
}
