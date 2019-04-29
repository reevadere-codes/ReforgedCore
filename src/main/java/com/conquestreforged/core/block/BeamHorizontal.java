package com.conquestreforged.core.block;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
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
public class BeamHorizontal extends BlockHorizontal {

    public static final IntegerProperty ACTIVATED = IntegerProperty.create("activated", 1, 4);

    //might make more voxelshapes later on...
    private static final VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 12.0D, 0.0D, 16.0D, 16.0D, 16.0D);

    public BeamHorizontal(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(IBlockState state, IBlockReader worldIn, BlockPos pos) {
        return SHAPE;
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
                .with(ACTIVATED, 0);
    }

    @Override
    public boolean onBlockActivated(IBlockState state, World worldIn, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!player.abilities.allowEdit) {
            return false;
        } else {
            worldIn.setBlockState(pos, state.cycle(ACTIVATED), 3);
            return true;
        }
    }
}
