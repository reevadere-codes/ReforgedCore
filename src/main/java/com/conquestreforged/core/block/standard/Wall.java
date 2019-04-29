package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.*;
import net.minecraft.block.BlockFence;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
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
public class Wall extends BlockFence {

    public Wall(Properties properties) {
        super(properties);
    }

    @Override
    public boolean onBlockActivated(IBlockState state, World worldIn, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote) {
            return true;
        } else {
            if ((state.get(NORTH)) || state.get(EAST) || state.get(SOUTH) || state.get(WEST)) {
                worldIn.setBlockState(pos, state.with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false));
            } else {
                IBlockState north = worldIn.getBlockState(pos.north());
                IBlockState east = worldIn.getBlockState(pos.east());
                IBlockState south = worldIn.getBlockState(pos.south());
                IBlockState west = worldIn.getBlockState(pos.west());

                boolean northFlag = this.attachesTo(north, north.getBlockFaceShape(worldIn, pos.north(), EnumFacing.SOUTH));
                boolean eastFlag = this.attachesTo(east, east.getBlockFaceShape(worldIn, pos.east(), EnumFacing.WEST));
                boolean southFlag = this.attachesTo(south, south.getBlockFaceShape(worldIn, pos.south(), EnumFacing.NORTH));
                boolean westFlag = this.attachesTo(west, west.getBlockFaceShape(worldIn, pos.west(), EnumFacing.EAST));

                worldIn.setBlockState(pos, state.with(NORTH, northFlag).with(EAST, eastFlag).with(SOUTH, southFlag).with(WEST, westFlag));
            }

            return true;
        }
    }

    @Override
    public IBlockState getStateForPlacement(BlockItemUseContext context) {
        IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
        return this.getDefaultState().with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
    }

    @Override
    public IBlockState updatePostPlacement(IBlockState stateIn, EnumFacing facing, IBlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }

        return stateIn;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockReader worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return face != EnumFacing.UP && face != EnumFacing.DOWN ? BlockFaceShape.MIDDLE_POLE_THICK : BlockFaceShape.CENTER_BIG;
    }
}
