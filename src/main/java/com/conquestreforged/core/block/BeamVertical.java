package com.conquestreforged.core.block;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

@Assets(
        state = @State(name = "%s_beam", template = "parent_beam"),
        item = @Model(name = "item/%s_beam", parent = "block/%s_beam", template = "item/parent_beam"),
        block = {
                @Model(name = "block/%s_beam", template = "block/parent_beam")
        }
)
public class BeamVertical extends BlockHorizontal {

    public static final BooleanProperty ACTIVATED = BooleanProperty.create("activated");

    private static final VoxelShape SHAPE_NORTH_OFF = Block.makeCuboidShape(0.0D, 0.0D, 5.0D, 4.0D, 16.0D, 11.0D);
    private static final VoxelShape SHAPE_SOUTH_OFF = Block.makeCuboidShape(12.0D, 0.0D, 5.0D, 16.0D, 16.0D, 11.0D);
    private static final VoxelShape SHAPE_WEST_OFF = Block.makeCuboidShape(5.0D, 0.0D, 12.0D, 11.0D, 16.0D, 16.0D);
    private static final VoxelShape SHAPE_EAST_OFF = Block.makeCuboidShape(5.0D, 0.0D, 0.0D, 11.0D, 16.0D, 4.0D);
    private static final VoxelShape SHAPE_NORTH_ON = Block.makeCuboidShape(0.0D, 0.0D, 5.0D, 1.0D, 16.0D, 11.0D);
    private static final VoxelShape SHAPE_SOUTH_ON = Block.makeCuboidShape(0.0D, 0.0D, 5.0D, 1.0D, 16.0D, 11.0D);
    private static final VoxelShape SHAPE_WEST_ON = Block.makeCuboidShape(5.0D, 0.0D, 0.0D, 11.0D, 16.0D, 16.0D);
    private static final VoxelShape SHAPE_EAST_ON = Block.makeCuboidShape(5.0D, 0.0D, 0.0D, 11.0D, 16.0D, 16.0D);

    public BeamVertical(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getCollisionShape(IBlockState state, IBlockReader worldIn, BlockPos pos) {
        return getShape(state);
    }

    @Override
    public VoxelShape getShape(IBlockState state, IBlockReader worldIn, BlockPos pos) {
        return getShape(state);
    }

    @Override
    public VoxelShape getRaytraceShape(IBlockState state, IBlockReader worldIn, BlockPos pos) {
        return getShape(state);
    }

    private VoxelShape getShape(IBlockState state) {
        if (state.get(ACTIVATED)) {
            switch (state.get(HORIZONTAL_FACING)) {
                case NORTH:
                default:
                    return SHAPE_NORTH_OFF;
                case SOUTH:
                    return SHAPE_SOUTH_OFF;
                case WEST:
                    return SHAPE_EAST_OFF;
                case EAST:
                    return SHAPE_WEST_OFF;
            }
        } else {
            switch (state.get(HORIZONTAL_FACING)) {
                case NORTH:
                default:
                    return SHAPE_NORTH_ON;
                case SOUTH:
                    return SHAPE_SOUTH_ON;
                case WEST:
                    return SHAPE_EAST_ON;
                case EAST:
                    return SHAPE_WEST_ON;
            }
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, IBlockState> builder) {
        builder.add(HORIZONTAL_FACING, ACTIVATED);
    }

    @Override
    public IBlockState getStateForPlacement(BlockItemUseContext context) {
        //IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
        EnumFacing facing = context.getPlacementHorizontalFacing().getOpposite();

        return super.getStateForPlacement(context)
                .with(HORIZONTAL_FACING, facing)
                .with(ACTIVATED, false);
    }

    @Override
    public boolean onBlockActivated(IBlockState state, World worldIn, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (state.get(ACTIVATED)) {
            state = state.with(ACTIVATED, Boolean.FALSE);
            worldIn.setBlockState(pos, state, 10);
        } else {
            state = state.with(ACTIVATED, Boolean.TRUE);
            worldIn.setBlockState(pos, state, 10);
        }

        worldIn.playEvent(player, state.get(ACTIVATED) ? 1008 : 1014, pos, 0);
        return true;
    }
}
