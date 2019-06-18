package com.conquestreforged.core.block;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

@Assets(
        state = @State(name = "%s_horizontalbeam", template = "parent_horizontalbeam"),
        item = @Model(name = "item/%s_horizontalbeam", parent = "block/%s_horizontalbeam", template = "item/parent_horizontalbeam"),
        block = {
                @Model(name = "block/%s_horizontalbeam", template = "block/parent_horizontalbeam")
        }
)
public class BeamHorizontal extends HorizontalBlock {

    public static final IntegerProperty ACTIVATED = IntegerProperty.create("activated", 1, 4);

    //might make more voxelshapes later on...
    private static final VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 12.0D, 0.0D, 16.0D, 16.0D, 16.0D);

    public BeamHorizontal(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, ACTIVATED);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        //IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
        Direction facing = context.getPlacementHorizontalFacing().getOpposite();

        return super.getStateForPlacement(context)
                .with(HORIZONTAL_FACING, facing)
                .with(ACTIVATED, 0);
    }

    @Override
    public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (!player.playerAbilities.allowEdit) {
            return false;
        } else {
            world.setBlockState(pos, state.cycle(ACTIVATED), 3);
            return true;
        }
    }
}
