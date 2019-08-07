package com.conquestreforged.core.block;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

import java.util.Random;

@Assets(
        state = @State(name = "%s", template = "parent_plant_dense_stackable"),
        item = @Model(name = "item/%s", parent = "block/%s_pane_ns", template = "item/parent_round_arch"),
        block = {
                @Model(name = "block/%s_plant_dense_model_1", template = "block/parent_plant_dense_model_1"),
                @Model(name = "block/%s_plant_dense_model_2", template = "block/parent_plant_dense_model_2"),
                @Model(name = "block/%s_plant_dense_model_3", template = "block/parent_plant_dense_model_3"),
                @Model(name = "block/%s_pane_ns", template = "block/parent_flatpane_ns"),
        }
)
public class PlantsDenseStackable extends Bush {
    private static final IntegerProperty RANDOM = IntegerProperty.create("random", 0, 2);

    public PlantsDenseStackable(Properties properties) {
        super(properties);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        IBlockReader iblockreader = context.getWorld();
        BlockPos blockpos = context.getPos();
        BlockPos up = blockpos.up();
        BlockPos down = blockpos.down();
        BlockState BlockStateUp = iblockreader.getBlockState(up);
        BlockState BlockStateDown = iblockreader.getBlockState(down);
        Random rand = new Random();
        int randomBlockstate = rand.nextInt(3);

        if (BlockStateUp.getBlock() instanceof PlantsDenseStackable) {
            randomBlockstate = BlockStateUp.get(RANDOM);
        } else if (BlockStateDown.getBlock() instanceof PlantsDenseStackable) {
            randomBlockstate = BlockStateDown.get(RANDOM);
        }

        return super.getStateForPlacement(context)
                .with(RANDOM, randomBlockstate);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(RANDOM, WATERLOGGED);
    }
}
