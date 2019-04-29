package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import net.minecraft.block.Block;
import net.minecraft.block.IBucketPickupHandler;
import net.minecraft.block.ILiquidContainer;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.Half;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

import java.util.Random;

@Assets(
        state = @State(name = "%s_thinslab", template = "parent_thinslab"),
        item = @Model(name = "item/%s_thinslab", parent = "block/%s_thinslab", template = "item/parent_thinslab"),
        block = {
                @Model(name = "block/%s_thinslab", template = "block/parent_thinslab"),
        }
)
public class ThinSlab extends Block implements IBucketPickupHandler, ILiquidContainer {

    private static final VoxelShape BOTTOM_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D);
    private static final VoxelShape TOP_SHAPE = Block.makeCuboidShape(0.0D, 13.0D, 0.0D, 16.0D, 16.0D, 16.0D);

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final EnumProperty TYPE_UPDOWN = EnumProperty.create("type", Half.class);

    public ThinSlab(Properties properties) {
        super(properties);
        this.setDefaultState(this.getDefaultState().with(TYPE_UPDOWN, Half.BOTTOM).with(WATERLOGGED, false));
    }

    @Override
    public int getOpacity(IBlockState state, IBlockReader reader, BlockPos pos) {
        return reader.getMaxLightLevel();
    }

    @Override
    public boolean propagatesSkylightDown(IBlockState p_200123_1_, IBlockReader p_200123_2_, BlockPos p_200123_3_) {
        return false;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, IBlockState> container) {
        container.add(new IProperty[]{TYPE_UPDOWN, WATERLOGGED});
    }

    @Override
    public VoxelShape getShape(IBlockState state, IBlockReader p_196244_2_, BlockPos p_196244_3_) {
        if (state.get(TYPE_UPDOWN) == Half.TOP) {
            return TOP_SHAPE;
        } else {
            return BOTTOM_SHAPE;
        }
    }

    @Override
    public boolean isTopSolid(IBlockState state) {
        return state.get(TYPE_UPDOWN) == Half.TOP;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockReader reader, IBlockState state, BlockPos pos, EnumFacing facing) {
        if (facing == EnumFacing.UP && state.get(TYPE_UPDOWN) == Half.TOP) {
            return BlockFaceShape.SOLID;
        } else {
            return facing == EnumFacing.DOWN && state.get(TYPE_UPDOWN) == Half.BOTTOM ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
        }
    }

    @Override
    public IBlockState getStateForPlacement(BlockItemUseContext context) {
        IFluidState fluid = context.getWorld().getFluidState(context.getPos());
        IBlockState state2 = this.getDefaultState().with(TYPE_UPDOWN, Half.BOTTOM).with(WATERLOGGED, fluid.getFluid() == Fluids.WATER);
        EnumFacing facing = context.getFace();
        return facing != EnumFacing.DOWN && (facing == EnumFacing.UP || (double)context.getHitY() <= 0.5D) ? state2 : state2.with(TYPE_UPDOWN, Half.TOP);
    }

    @Override
    public int quantityDropped(IBlockState state, Random rand) {
        return 1;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isReplaceable(IBlockState state, BlockItemUseContext context) {
        ItemStack item = context.getItem();
        if (item.getItem() == this.asItem()) {
            if (context.replacingClickedOnBlock()) {
                boolean posBool = (double)context.getHitY() > 0.5D;
                EnumFacing facing = context.getFace();
                if (state.get(TYPE_UPDOWN) == Half.BOTTOM) {
                    return facing == EnumFacing.UP || posBool && facing.getAxis().isHorizontal();
                } else {
                    return facing == EnumFacing.DOWN || !posBool && facing.getAxis().isHorizontal();
                }
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean canContainFluid(IBlockReader worldIn, BlockPos pos, IBlockState state, Fluid fluidIn) {
        return !state.get(WATERLOGGED) && fluidIn == Fluids.WATER;
    }

    @Override
    public boolean receiveFluid(IWorld worldIn, BlockPos pos, IBlockState state, IFluidState fluidStateIn) {
        if (!state.get(WATERLOGGED) && fluidStateIn.getFluid() == Fluids.WATER) {
            if (!worldIn.isRemote()) {
                worldIn.setBlockState(pos, state.with(WATERLOGGED,true), 3);
                worldIn.getPendingFluidTicks().scheduleTick(pos, fluidStateIn.getFluid(), fluidStateIn.getFluid().getTickRate(worldIn));
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean allowsMovement(IBlockState state, IBlockReader reader, BlockPos pos, PathType pathType) {
        switch(pathType.ordinal()) {
            case 1:
                return state.get(TYPE_UPDOWN) == Half.BOTTOM;
            case 2:
                return reader.getFluidState(pos).isTagged(FluidTags.WATER);
            case 3:
                return false;
            default:
                return false;
        }
    }

    @Override
    public Fluid pickupFluid(IWorld world, BlockPos pos, IBlockState state) {
        if (state.get(WATERLOGGED)) {
            world.setBlockState(pos, state.with(WATERLOGGED, false), 3);
            return Fluids.WATER;
        } else {
            return Fluids.EMPTY;
        }
    }

    @Override
    public IFluidState getFluidState(IBlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }
}