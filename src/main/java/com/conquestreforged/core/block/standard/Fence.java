package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.LeadItem;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
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

}