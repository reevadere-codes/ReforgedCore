package com.conquestreforged.core.item;

import com.conquestreforged.core.item.family.Family;
import com.conquestreforged.core.item.family.FamilyRegistry;
import com.conquestreforged.core.item.family.TypeFilter;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.Optional;

public class ItemUtils {

    public static <T extends Item> Optional<T> toItem(Item item, Class<T> t) {
        return t.isInstance(item) ? Optional.of(t.cast(item)) : Optional.empty();
    }

    public static Optional<ItemBlock> toItemBlock(Item item) {
        return toItem(item, ItemBlock.class);
    }

    public static Optional<MultiItemBlock> toMultiItem(Item item) {
        return toItem(item, MultiItemBlock.class);
    }

    public static NonNullList<ItemStack> getFamilyItems(ItemStack stack) {
        return getFamilyItems(stack, TypeFilter.ANY);
    }

    public static NonNullList<ItemStack> getFamilyItems(ItemStack stack, TypeFilter filter) {
        NonNullList<ItemStack> items = NonNullList.create();
        getFamily(stack).addAllItems(ItemGroup.SEARCH, items, filter);
        if (items.isEmpty()) {
            items.add(stack);
        }
        return items;
    }

    public static Family<Block> getFamily(ItemStack stack) {
        Item item = stack.getItem();
        Block block = Blocks.AIR;
        if (item instanceof ItemBlock) {
            block = ((ItemBlock) item).getBlock();
        }
        return FamilyRegistry.BLOCKS.getFamily(block);
    }
}
