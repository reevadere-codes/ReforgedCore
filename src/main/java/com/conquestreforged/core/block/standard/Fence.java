package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.*;
import net.minecraft.block.BlockFence;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Fluids;
import net.minecraft.init.Items;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemLead;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
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
public class Fence extends BlockFence {

    public Fence(Properties properties) {
        super(properties);
    }

    @Override
    public boolean onBlockActivated(IBlockState state, World worldIn, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {

        if (player.getHeldItem(hand).getItem() == Items.LEAD) {
            if (!worldIn.isRemote) {
                return ItemLead.attachToFence(player, worldIn, pos);
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
        //a test for a toggle
        /*EntityPlayer player = context.getPlayer();
        Collection<PotionEffect> effects = player.getActivePotionEffects();
        if (effects.contains(player.getActivePotionEffect(Potion.getPotionById(1)))) {
            IBlockReader iblockreader = context.getWorld();
            BlockPos blockpos = context.getPos();
            IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
            return super.getStateForPlacement(context)
                    .with(NORTH, this.canFenceConnectTo(iblockreader, blockpos, EnumFacing.NORTH))
                    .with(EAST, this.canFenceConnectTo(iblockreader, blockpos, EnumFacing.EAST))
                    .with(SOUTH, this.canFenceConnectTo(iblockreader, blockpos, EnumFacing.SOUTH))
                    .with(WEST, this.canFenceConnectTo(iblockreader, blockpos, EnumFacing.WEST))
                    .with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);

        }*/
        IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
        return this.getDefaultState().with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
    }

    /*
    private boolean canFenceConnectTo(IBlockReader p_canFenceConnectTo_1_, BlockPos p_canFenceConnectTo_2_, EnumFacing p_canFenceConnectTo_3_) {
        BlockPos offset = p_canFenceConnectTo_2_.offset(p_canFenceConnectTo_3_);
        IBlockState other = p_canFenceConnectTo_1_.getBlockState(offset);
        return other.canBeConnectedTo(p_canFenceConnectTo_1_, offset, p_canFenceConnectTo_3_.getOpposite()) || this.getDefaultState().canBeConnectedTo(p_canFenceConnectTo_1_, p_canFenceConnectTo_2_, p_canFenceConnectTo_3_);
    }*/

    @Override
    public IBlockState updatePostPlacement(IBlockState stateIn, EnumFacing facing, IBlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }

        return stateIn;
    }
}