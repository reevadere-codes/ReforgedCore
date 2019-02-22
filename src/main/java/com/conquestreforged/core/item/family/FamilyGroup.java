package com.conquestreforged.core.item.family;

import com.conquestreforged.core.item.group.TaggedGroup;
import com.conquestreforged.core.util.Stack;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

public class FamilyGroup extends TaggedGroup<FamilyGroup> {

    public static final List<FamilyGroup> FAMILY_GROUPS = new LinkedList<>();
    private static Family.Filler filler = Family::addAllItems;

    private final Supplier<ItemStack> icon;

    public FamilyGroup(int order, String label, String icon) {
        this(order, label, Stack.block(icon));
    }

    public FamilyGroup(int order, String label, Supplier<ItemStack> icon) {
        super(order, label);
        this.icon = icon;
        FAMILY_GROUPS.add(this);
    }

    @Override
    public FamilyGroup self() {
        return this;
    }

    @Override
    public ItemStack createIcon() {
        return icon.get();
    }

    @Override
    public void fill(NonNullList<ItemStack> items) {
        for (Family<Block> family : FamilyRegistry.BLOCKS.values()) {
            filler.fill(family, this, items);
        }
        addTaggedBlocks(items);
        for (Family<Item> family : FamilyRegistry.ITEMS.values()) {
            filler.fill(family, this, items);
        }
        addTaggedItems(items);
    }

    public static void addAllItems() {
        filler = Family::addAllItems;
    }

    public static void addRootItems() {
        filler = Family::addRootItem;
    }
}
