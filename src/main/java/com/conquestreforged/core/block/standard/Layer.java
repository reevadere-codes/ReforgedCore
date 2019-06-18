package com.conquestreforged.core.block.standard;


import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import net.minecraft.block.BlockSnowLayer;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.Random;

@Assets(
        state = @State(name = "%s_layer", template = "snow"),
        item = @Model(name = "item/%s_layer", parent = "block/%s_layer_height2", template = "item/snow"),
        block = {
                @Model(name = "block/%s_layer_height2", template = "block/snow_height2"),
                @Model(name = "block/%s_layer_height4", template = "block/snow_height4"),
                @Model(name = "block/%s_layer_height6", template = "block/snow_height6"),
                @Model(name = "block/%s_layer_height8", template = "block/snow_height8"),
                @Model(name = "block/%s_layer_height10", template = "block/snow_height10"),
                @Model(name = "block/%s_layer_height12", template = "block/snow_height12"),
                @Model(name = "block/%s_layer_height14", template = "block/snow_height14"),
                @Model(name = "block/%s_layer_height16", template = "block/snow_block"),
        }
)
public class Layer extends BlockSnowLayer {

    public Layer(Properties properties) {
        super(properties);
    }

    @Override
    public IItemProvider getItemDropped(BlockState state, World world, BlockPos pos, int number) {
        return this;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader reader, BlockPos pos) {
        return SHAPES[state.get(LAYERS)];
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReaderBase reader, BlockPos pos) {
        return true;
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState state1, IWorld world, BlockPos pos, BlockPos pos1) {
        return state;
    }

    @Override
    public void tick(BlockState state, World world, BlockPos pos, Random rand) {
    }

    @Override
    protected boolean canSilkHarvest() {
        return false;
    }

    @Override
    public void harvestBlock(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack itemblock) {
        Integer integer = state.get(LAYERS);
        NonNullList<ItemStack> items = NonNullList.create();
        float chance = 1.0F;
        for(int i = 0; i < integer; ++i) {
            items.add(this.getSilkTouchDrop(state));
        }

        Iterator var13 = items.iterator();

        while(var13.hasNext()) {
            ItemStack item = (ItemStack)var13.next();
            if (world.rand.nextFloat() <= chance) {
                spawnAsEntity(world, pos, item);
            }
        }

        world.removeBlock(pos);
        player.addStat(StatList.BLOCK_MINED.get(this));
        player.addExhaustion(0.005F);
    }

    @Override
    public boolean isReplaceable(BlockState state, BlockItemUseContext ctx) {
        int i = state.get(LAYERS);
        if (ctx.getItem().getItem() == this.asItem() && i < 8) {
            if (ctx.replacingClickedOnBlock()) {
                return ctx.getFace() == Direction.UP;
            } else {
                return false;
            }
        } else {
            return i == 1;
        }
    }
}