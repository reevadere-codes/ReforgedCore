package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.*;
import net.minecraft.block.BlockState;
import net.minecraft.block.FenceBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.LeadItem;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

@Assets(
        state = @State(name = "%s_fence", template = "acacia_fence"),
        item = @Model(name = "item/%s_fence", parent = "block/%s_fence_inventory", template = "item/acacia_fence"),
        block = {
                @Model(name = "block/%s_fence_post", template = "block/acacia_fence_post"),
                @Model(name = "block/%s_fence_side", template = "block/acacia_fence_side"),
                @Model(name = "block/%s_fence_inventory", template = "block/acacia_fence_inventory"),
        },
        recipe = @Recipe(
                name = "%s_fence",
                template = "acacia_fence",
                ingredients = {
                        @Ingredient(name = "%s", template = "acacia_planks", plural = true)
                }
        )
)
public class Fence extends FenceBlock {

    public Fence(Properties properties) {
        super(properties);
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (player.getHeldItem(hand).getItem() == Items.LEAD) {
            if (!worldIn.isRemote) {
                return LeadItem.attachToFence(player, worldIn, pos);
            } else {
                ItemStack itemstack = player.getHeldItem(hand);
                return itemstack.getItem() == Items.LEAD || itemstack.isEmpty();
            }
        }

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

//                boolean northFlag = func_220111_a(north, true, north.getBlockFaceShape(worldIn, pos.north(), Direction.SOUTH));
//                boolean eastFlag = func_220111_a(east, true, east.getBlockFaceShape(worldIn, pos.east(), Direction.WEST));
//                boolean southFlag = func_220111_a(south, true, south.getBlockFaceShape(worldIn, pos.south(), Direction.NORTH));
//                boolean westFlag = func_220111_a(west, true, west.getBlockFaceShape(worldIn, pos.west(), Direction.EAST));
//
//                worldIn.setBlockState(pos, state.with(NORTH, northFlag).with(EAST, eastFlag).with(SOUTH, southFlag).with(WEST, westFlag));
            }

            return true;
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        //a test for a toggle
        /*PlayerEntity player = context.getPlayer();
        Collection<PotionEffect> effects = player.getActivePotionEffects();
        if (effects.contains(player.getActivePotionEffect(Potion.getPotionById(1)))) {
            IBlockReader iblockreader = context.getWorld();
            BlockPos blockpos = context.getPos();
            IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
            return super.getStateForPlacement(context)
                    .with(NORTH, this.canFenceConnectTo(iblockreader, blockpos, Direction.NORTH))
                    .with(EAST, this.canFenceConnectTo(iblockreader, blockpos, Direction.EAST))
                    .with(SOUTH, this.canFenceConnectTo(iblockreader, blockpos, Direction.SOUTH))
                    .with(WEST, this.canFenceConnectTo(iblockreader, blockpos, Direction.WEST))
                    .with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);

        }*/
        IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
        return this.getDefaultState().with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
    }

    /*
    private boolean canFenceConnectTo(IBlockReader p_canFenceConnectTo_1_, BlockPos p_canFenceConnectTo_2_, Direction p_canFenceConnectTo_3_) {
        BlockPos offset = p_canFenceConnectTo_2_.offset(p_canFenceConnectTo_3_);
        BlockState other = p_canFenceConnectTo_1_.getBlockState(offset);
        return other.canBeConnectedTo(p_canFenceConnectTo_1_, offset, p_canFenceConnectTo_3_.getOpposite()) || this.getDefaultState().canBeConnectedTo(p_canFenceConnectTo_1_, p_canFenceConnectTo_2_, p_canFenceConnectTo_3_);
    }*/

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }

        return stateIn;
    }
}