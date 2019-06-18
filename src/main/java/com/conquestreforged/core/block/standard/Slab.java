package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.*;
import net.minecraft.block.Block;
import net.minecraft.block.IBucketPickupHandler;
import net.minecraft.block.ILiquidContainer;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockState;
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
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

import java.util.Random;

@Assets(
        state = @State(name = "%s_slab", template = "parent_slab"),
        item = @Model(name = "item/%s_slab", parent = "block/%s_slab", template = "item/acacia_slab"),
        block = {
                @Model(name = "block/%s_slab", template = "block/parent_slab"),
        },
        recipe = @Recipe(
                name = "%s_slab",
                template = "acacia_slab",
                ingredients = {
                        @Ingredient(name = "%s", template = "acacia_planks", plural = true)
                }
        )
)
public class Slab extends Block implements IBucketPickupHandler, ILiquidContainer {

    private static final VoxelShape BOTTOM_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
    private static final VoxelShape TOP_SHAPE = Block.makeCuboidShape(0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D);

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final EnumProperty TYPE_UPDOWN = EnumProperty.create("type", Half.class);
    private Block fullBlock;

    public Slab(Properties properties) {
        super(properties);
        this.setDefaultState(this.getDefaultState().with(TYPE_UPDOWN, Half.BOTTOM).with(WATERLOGGED, false));
    }

    @Override
    public int getOpacity(BlockState state, IBlockReader reader, BlockPos pos) {
        return reader.getMaxLightLevel();
    }

    @Override
    public boolean propagatesSkylightDown(BlockState p_200123_1_, IBlockReader p_200123_2_, BlockPos p_200123_3_) {
        return false;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> container) {
        container.add(new IProperty[]{TYPE_UPDOWN, WATERLOGGED});
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader p_196244_2_, BlockPos p_196244_3_) {
        if (state.get(TYPE_UPDOWN) == Half.TOP) {
            return TOP_SHAPE;
        } else {
            return BOTTOM_SHAPE;
        }
    }

    @Override
    public boolean isTopSolid(BlockState state) {
        return state.get(TYPE_UPDOWN) == Half.TOP;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockReader reader, BlockState state, BlockPos pos, Direction facing) {
        if (facing == Direction.UP && state.get(TYPE_UPDOWN) == Half.TOP) {
            return BlockFaceShape.SOLID;
        } else {
            return facing == Direction.DOWN && state.get(TYPE_UPDOWN) == Half.BOTTOM ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState state = context.getWorld().getBlockState(context.getPos());
        if (state.getBlock() == this) {
            return fullBlock.getDefaultState();
        } else {
            IFluidState fluid = context.getWorld().getFluidState(context.getPos());
            BlockState state2 = this.getDefaultState().with(TYPE_UPDOWN, Half.BOTTOM).with(WATERLOGGED, fluid.getFluid() == Fluids.WATER);
            Direction facing = context.getFace();
            return facing != Direction.DOWN && (facing == Direction.UP || (double)context.getHitY() <= 0.5D) ? state2 : state2.with(TYPE_UPDOWN, Half.TOP);
        }
    }

    @Override
    public int quantityDropped(BlockState state, Random rand) {
        return 1;
    }

    @Override
    public boolean isFullCube(BlockState state) {
        return false;
    }

    @Override
    public boolean isReplaceable(BlockState state, BlockItemUseContext context) {
        ItemStack item = context.getItem();
        if (item.getItem() == this.asItem()) {
            if (context.replacingClickedOnBlock()) {
                boolean posBool = (double)context.getHitY() > 0.5D;
                Direction facing = context.getFace();
                if (state.get(TYPE_UPDOWN) == Half.BOTTOM) {
                    return facing == Direction.UP || posBool && facing.getAxis().isHorizontal();
                } else {
                    return facing == Direction.DOWN || !posBool && facing.getAxis().isHorizontal();
                }
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean canContainFluid(IBlockReader worldIn, BlockPos pos, BlockState state, Fluid fluidIn) {
        return !state.get(WATERLOGGED) && fluidIn == Fluids.WATER;
    }

    @Override
    public boolean receiveFluid(IWorld worldIn, BlockPos pos, BlockState state, IFluidState fluidStateIn) {
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
    public boolean allowsMovement(BlockState state, IBlockReader reader, BlockPos pos, PathType pathType) {
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
    public Fluid pickupFluid(IWorld world, BlockPos pos, BlockState state) {
        if (state.get(WATERLOGGED)) {
            world.setBlockState(pos, state.with(WATERLOGGED, false), 3);
            return Fluids.WATER;
        } else {
            return Fluids.EMPTY;
        }
    }

    @Override
    public IFluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }
}