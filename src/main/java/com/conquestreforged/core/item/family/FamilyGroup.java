package com.conquestreforged.core.item.family;

import com.conquestreforged.core.item.group.TaggedGroup;
import com.conquestreforged.core.util.Stack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

public class FamilyGroup extends TaggedGroup<FamilyGroup> {

    public static final List<FamilyGroup> FAMILY_GROUPS = new LinkedList<>();
    private static final Family.Filler ALL_ITEMS = Family::addAllItems;
    private static final Family.Filler ROOT_ITEMS = Family::addRootItem;
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
    public void populate(NonNullList<ItemStack> items) {
        FamilyRegistry.BLOCKS.values().forEach(family -> filler.fill(family, this, items));
        addTaggedBlocks(items);

        FamilyRegistry.ITEMS.values().forEach(family -> filler.fill(family, this, items));
    }

    public static void setAddAllItems() {
        if (filler != ALL_ITEMS) {
            filler = ALL_ITEMS;
            FAMILY_GROUPS.forEach(FamilyGroup::invalidate);
        }
    }

    public static void setAddRootItems() {
        if (filler != ROOT_ITEMS) {
            filler = ROOT_ITEMS;
            FAMILY_GROUPS.forEach(FamilyGroup::invalidate);
        }
    }
}
