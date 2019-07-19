package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import net.minecraft.block.WallBlock;

@Assets(
        state = @State(name = "%s_small_balustrade", template = "acacia_fence"),
        item = @Model(name = "item/%s_small_balustrade", parent = "block/%s_balustrade_small_inventory", template = "item/acacia_fence"),
        block = {
                @Model(name = "block/%s_balustrade_small_post", template = "block/acacia_fence_post"),
                @Model(name = "block/%s_balustrade_small_side", template = "block/acacia_fence_side"),
                @Model(name = "block/%s_balustrade_small_inventory", template = "block/acacia_fence_inventory"),
        }
)
public class BalustradeSmall extends WallBlock {

    public BalustradeSmall(Properties properties) {
        super(properties);
    }

    //Places as a pillar
//    @Override
//    public BlockState getStateForPlacement(BlockItemUseContext context) {
//        World world = context.getWorld();
//        BlockPos blockpos = context.getPos();
//        IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
//        BlockPos blockpos1 = blockpos.north();
//        BlockPos blockpos2 = blockpos.east();
//        BlockPos blockpos3 = blockpos.south();
//        BlockPos blockpos4 = blockpos.west();
//        BlockState north = world.getBlockState(blockpos.north());
//        BlockState east = world.getBlockState(blockpos.east());
//        BlockState south = world.getBlockState(blockpos.south());
//        BlockState west = world.getBlockState(blockpos.west());
//        boolean flag = this.attachesTo(north, north.getBlockFaceShape(world, blockpos1, Direction.SOUTH));
//        boolean flag1 = this.attachesTo(east, east.getBlockFaceShape(world, blockpos2, Direction.WEST));
//        boolean flag2 = this.attachesTo(south, south.getBlockFaceShape(world, blockpos3, Direction.NORTH));
//        boolean flag3 = this.attachesTo(west, west.getBlockFaceShape(world, blockpos4, Direction.EAST));
//        boolean flag4 = (!flag || flag1 || !flag2 || flag3) && (flag || !flag1 || flag2 || !flag3);
//        return this.getDefaultState().with(UP, (flag4 || !world.isAirBlock(blockpos.up()))).with(WATERLOGGED, (ifluidstate.getFluid() == Fluids.WATER));
//    }

    //Adds wall connections, changing it from a pillar to a wall. Done by right-click right now. In the future it'll be through a chisel that potentially consumes a bit of the original full block
//    @Override
//    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos currentPos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
//        if (worldIn.isRemote) {
//            return true;
//        } else {
//            if ((state.get(NORTH)) || state.get(EAST) || state.get(SOUTH) || state.get(WEST)) {
//                worldIn.setBlockState(currentPos, state.with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false));
//            } else {
//                BlockState north = worldIn.getBlockState(currentPos.north());
//                BlockState east = worldIn.getBlockState(currentPos.east());
//                BlockState south = worldIn.getBlockState(currentPos.south());
//                BlockState west = worldIn.getBlockState(currentPos.west());
//
//                boolean northFlag = this.attachesTo(north, north.getBlockFaceShape(worldIn, currentPos.north(), Direction.SOUTH));
//                boolean eastFlag = this.attachesTo(east, east.getBlockFaceShape(worldIn, currentPos.east(), Direction.WEST));
//                boolean southFlag = this.attachesTo(south, south.getBlockFaceShape(worldIn, currentPos.south(), Direction.NORTH));
//                boolean westFlag = this.attachesTo(west, west.getBlockFaceShape(worldIn, currentPos.west(), Direction.EAST));
//
//                worldIn.setBlockState(currentPos, state.with(NORTH, northFlag).with(EAST, eastFlag).with(SOUTH, southFlag).with(WEST, westFlag));
//            }
//
//            return true;
//        }
//    }

    //Only the UP blockstate updates when neighbors are changed
//    @Override
//    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
//        if (stateIn.get(WATERLOGGED)) {
//            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
//        }
//        if (facing == Direction.DOWN) {
//            return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
//        } else {
//
//            boolean flag = facing == Direction.NORTH ? this.attachesTo(facingState, facingState.getBlockFaceShape(worldIn, facingPos, facing.getOpposite())) : stateIn.get(NORTH);
//            boolean flag1 = facing == Direction.EAST ? this.attachesTo(facingState, facingState.getBlockFaceShape(worldIn, facingPos, facing.getOpposite())) : stateIn.get(EAST);
//            boolean flag2 = facing == Direction.SOUTH ? this.attachesTo(facingState, facingState.getBlockFaceShape(worldIn, facingPos, facing.getOpposite())) : stateIn.get(SOUTH);
//            boolean flag3 = facing == Direction.WEST ? this.attachesTo(facingState, facingState.getBlockFaceShape(worldIn, facingPos, facing.getOpposite())) : stateIn.get(WEST);
//            boolean flag4 = (!flag || flag1 || !flag2 || flag3) && (flag || !flag1 || flag2 || !flag3);
//
//
//            return stateIn.with(UP, flag4 || !worldIn.isAirBlock(currentPos.up()));
//        }
//    }

//    private boolean attachesTo(BlockState blockstate, BlockFaceShape shape) {
//        Block block = blockstate.getBlock();
//        boolean flag = shape == BlockFaceShape.MIDDLE_POLE_THICK || shape == BlockFaceShape.MIDDLE_POLE && (block instanceof BlockFenceGate || block instanceof FenceGate);
//        return !isExcepBlockForAttachWithPiston(block) && shape == BlockFaceShape.SOLID || flag;
//    }

}
