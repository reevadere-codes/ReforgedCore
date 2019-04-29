package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;

/**
 * Orientable cube with the following faces:
 * - Front -> North
 * - Side  -> East/South/West
 * - Top   -> Top/Bottom
 */
@Assets(
        state = @State(name = "%s", template = "anvil", plural = true),
        item = @Model(name = "item/%s", parent = "block/%s", template = "item/acacia_planks"),
        block = @Model(name = "block/%s", template = "block/orientable")
)
public class CubeOrientable extends Block {

    private static final DirectionProperty FACING = BlockHorizontal.HORIZONTAL_FACING;

    public CubeOrientable(Block.Properties properties) {
        super(properties);
    }

    public IBlockState rotate(IBlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    public IBlockState mirror(IBlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    protected void fillStateContainer(StateContainer.Builder<Block, IBlockState> builder) {
        builder.add(FACING);
    }
}
