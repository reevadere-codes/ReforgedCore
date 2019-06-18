package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import com.conquestreforged.core.block.extensions.Waterloggable;
import com.conquestreforged.core.block.types.CapitalDirection;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.Half;
import net.minecraft.util.Direction;

@Assets(
        state = @State(name = "%s_sphere", template = "parent_sphere"),
        item = @Model(name = "item/%s_sphere", parent = "block/%s_sphere", template = "item/parent_sphere"),
        block = {
                @Model(name = "block/%s_sphere", template = "block/parent_sphere"),
                @Model(name = "block/%s_sphere_dragonegg", template = "block/parent_sphere"),
                @Model(name = "block/%s_sphere_small", template = "block/parent_sphere"),
        }
)
public class Sphere extends Block implements Waterloggable {

    public static final EnumProperty<CapitalDirection> FACING = EnumProperty.create("facing", CapitalDirection.class);
    public static final EnumProperty<Half> TYPE = EnumProperty.create("type", Half.class);

    public Sphere(Properties properties) {
        super(properties);
        this.setDefaultState((this.stateContainer.getBaseState()).with(TYPE, Half.TOP).with(FACING, CapitalDirection.NORTH).with(WATERLOGGED, false));

    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(TYPE, FACING, WATERLOGGED);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
        Direction facing = context.getFace();

        CapitalDirection horizontalFacing;
        switch (facing) {
            default:
                horizontalFacing = CapitalDirection.FLAT;
                break;
            case NORTH:
                horizontalFacing = CapitalDirection.SOUTH;
                break;
            case SOUTH:
                horizontalFacing = CapitalDirection.NORTH;
                break;
            case WEST:
                horizontalFacing = CapitalDirection.EAST;
                break;
            case EAST:
                horizontalFacing = CapitalDirection.WEST;
                break;
        }

        Half verticalFacing;
        if (facing != Direction.DOWN && (facing == Direction.UP || context.func_221532_j().y <= 0.5D)) {
            verticalFacing = Half.BOTTOM;
        } else {
            verticalFacing = Half.TOP;
        }

        return super.getStateForPlacement(context)
                .with(TYPE, verticalFacing)
                .with(FACING, horizontalFacing)
                .with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
    }

    @Override
    public IFluidState getFluidState(BlockState state) {
        return Waterloggable.getFluidState(state);
    }
}
