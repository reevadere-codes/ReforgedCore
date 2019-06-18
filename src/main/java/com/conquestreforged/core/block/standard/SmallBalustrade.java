package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockWall;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;

@Assets(
        state = @State(name = "%s_fence", template = "acacia_fence"),
        item = @Model(name = "item/%s_fence", parent = "block/%s_fence_inventory", template = "item/acacia_fence"),
        block = {
                @Model(name = "block/%s_fence_post", template = "block/acacia_fence_post"),
                @Model(name = "block/%s_fence_side", template = "block/acacia_fence_side"),
                @Model(name = "block/%s_fence_inventory", template = "block/acacia_fence_inventory"),
        }
)
public class SmallBalustrade extends BlockWall {

    public SmallBalustrade (Properties properties) {
        super(properties);
    }

    //Places as a pillar
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        IWorldReaderBase iworldreaderbase = context.getWorld();
        BlockPos blockpos = context.getPos();
        IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
        BlockPos blockpos1 = blockpos.north();
        BlockPos blockpos2 = blockpos.east();
        BlockPos blockpos3 = blockpos.south();
        BlockPos blockpos4 = blockpos.west();
        BlockState BlockState = iworldreaderbase.getBlockState(blockpos1);
        BlockState BlockState1 = iworldreaderbase.getBlockState(blockpos2);
        BlockState BlockState2 = iworldreaderbase.getBlockState(blockpos3);
        BlockState BlockState3 = iworldreaderbase.getBlockState(blockpos4);
        boolean flag = this.attachesTo(BlockState, BlockState.getBlockFaceShape(iworldreaderbase, blockpos1, Direction.SOUTH));
        boolean flag1 = this.attachesTo(BlockState1, BlockState1.getBlockFaceShape(iworldreaderbase, blockpos2, Direction.WEST));
        boolean flag2 = this.attachesTo(BlockState2, BlockState2.getBlockFaceShape(iworldreaderbase, blockpos3, Direction.NORTH));
        boolean flag3 = this.attachesTo(BlockState3, BlockState3.getBlockFaceShape(iworldreaderbase, blockpos4, Direction.EAST));
        boolean flag4 = (!flag || flag1 || !flag2 || flag3) && (flag || !flag1 || flag2 || !flag3);
        return this.getDefaultState().with(UP, (flag4 || !iworldreaderbase.isAirBlock(blockpos.up()))).with(WATERLOGGED, (ifluidstate.getFluid() == Fluids.WATER));
    }

    //Adds wall connections, changing it from a pillar to a wall. Done by right-click right now. In the future it'll be through a chisel that potentially consumes a bit of the original full block
    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos currentPos, PlayerEntity player, Hand hand, Direction facing, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote) {
            return true;
        } else {
            if ((state.get(NORTH)) || state.get(EAST) || state.get(SOUTH) || state.get(WEST)) {
                worldIn.setBlockState(currentPos, state.with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false));
            } else {
                BlockState north = worldIn.getBlockState(currentPos.north());
                BlockState east = worldIn.getBlockState(currentPos.east());
                BlockState south = worldIn.getBlockState(currentPos.south());
                BlockState west = worldIn.getBlockState(currentPos.west());

                boolean northFlag = this.attachesTo(north, north.getBlockFaceShape(worldIn, currentPos.north(), Direction.SOUTH));
                boolean eastFlag = this.attachesTo(east, east.getBlockFaceShape(worldIn, currentPos.east(), Direction.WEST));
                boolean southFlag = this.attachesTo(south, south.getBlockFaceShape(worldIn, currentPos.south(), Direction.NORTH));
                boolean westFlag = this.attachesTo(west, west.getBlockFaceShape(worldIn, currentPos.west(), Direction.EAST));

                worldIn.setBlockState(currentPos, state.with(NORTH, northFlag).with(EAST, eastFlag).with(SOUTH, southFlag).with(WEST, westFlag));
            }

            return true;
        }
    }

    //Only the UP blockstate updates when neighbors are changed
    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }
        if (facing == Direction.DOWN) {
            return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        } else {

            boolean flag = facing == Direction.NORTH ? this.attachesTo(facingState, facingState.getBlockFaceShape(worldIn, facingPos, facing.getOpposite())) : stateIn.get(NORTH);
            boolean flag1 = facing == Direction.EAST ? this.attachesTo(facingState, facingState.getBlockFaceShape(worldIn, facingPos, facing.getOpposite())) : stateIn.get(EAST);
            boolean flag2 = facing == Direction.SOUTH ? this.attachesTo(facingState, facingState.getBlockFaceShape(worldIn, facingPos, facing.getOpposite())) : stateIn.get(SOUTH);
            boolean flag3 = facing == Direction.WEST ? this.attachesTo(facingState, facingState.getBlockFaceShape(worldIn, facingPos, facing.getOpposite())) : stateIn.get(WEST);
            boolean flag4 = (!flag || flag1 || !flag2 || flag3) && (flag || !flag1 || flag2 || !flag3);


            return stateIn.with(UP, flag4 || !worldIn.isAirBlock(currentPos.up()));
        }
    }

    private boolean attachesTo(BlockState blockstate, BlockFaceShape shape) {
        Block block = blockstate.getBlock();
        boolean flag = shape == BlockFaceShape.MIDDLE_POLE_THICK || shape == BlockFaceShape.MIDDLE_POLE && (block instanceof BlockFenceGate || block instanceof FenceGate);
        return !isExcepBlockForAttachWithPiston(block) && shape == BlockFaceShape.SOLID || flag;
    }

}
