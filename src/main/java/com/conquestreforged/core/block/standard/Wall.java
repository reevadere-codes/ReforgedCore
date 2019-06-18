package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.*;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

@Assets(
        state = @State(name = "%s_wall", template = "cobblestone_wall"),
        item = @Model(name = "item/%s_wall", parent = "block/%s_wall_inventory", template = "item/cobblestone_wall"),
        block = {
                @Model(name = "block/%s_wall_post", template = "block/cobblestone_wall_post"),
                @Model(name = "block/%s_wall_side", template = "block/cobblestone_wall_side"),
                @Model(name = "block/%s_wall_inventory", template = "block/cobblestone_wall_inventory"),
        },
        recipe = @Recipe(
                name = "%s_wall",
                template = "cobblestone_wall",
                ingredients = {
                        @Ingredient(name = "%s", template = "cobblestone", plural = true)
                }
        )
)
public class Wall extends WallBlock {

    public Wall(Properties properties) {
        super(properties);
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (worldIn.isRemote) {
            return true;
        } else {
            if ((state.get(NORTH)) || state.get(EAST) || state.get(SOUTH) || state.get(WEST)) {
                worldIn.setBlockState(pos, state.with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false));
            } else {
                BlockState north = worldIn.getBlockState(pos.north());
                BlockState east = worldIn.getBlockState(pos.east());
                BlockState south = worldIn.getBlockState(pos.south());
                BlockState west = worldIn.getBlockState(pos.west());

                boolean northFlag = this.attachesTo(north, north.getBlockFaceShape(worldIn, pos.north(), Direction.SOUTH));
                boolean eastFlag = this.attachesTo(east, east.getBlockFaceShape(worldIn, pos.east(), Direction.WEST));
                boolean southFlag = this.attachesTo(south, south.getBlockFaceShape(worldIn, pos.south(), Direction.NORTH));
                boolean westFlag = this.attachesTo(west, west.getBlockFaceShape(worldIn, pos.west(), Direction.EAST));

                worldIn.setBlockState(pos, state.with(NORTH, northFlag).with(EAST, eastFlag).with(SOUTH, southFlag).with(WEST, westFlag));
            }

            return true;
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
        return this.getDefaultState().with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }

        return stateIn;
    }
}
