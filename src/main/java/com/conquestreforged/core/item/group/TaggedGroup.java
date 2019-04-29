package com.conquestreforged.core.item.group;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.Tag;
import net.minecraft.util.NonNullList;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public abstract class TaggedGroup<T extends TaggedGroup> extends ConquestItemGroup {

    private final List<Tag<Block>> blocks = new LinkedList<>();
    private final List<Tag<Item>> items = new LinkedList<>();

    public TaggedGroup(int index, String label) {
        super(index, label);
    }

    public abstract  T self();

    @SafeVarargs
    public final T blocks(Tag<Block>... blocks) {
        Collections.addAll(this.blocks, blocks);
        return self();
    }

    @SafeVarargs
    public final T items(Tag<Item>... items) {
        Collections.addAll(this.items, items);
        return self();
    }

    public void addTaggedBlocks(NonNullList<ItemStack> items) {
        for (Tag<Block> tag : this.blocks) {
            tag.getAllElements().forEach(block -> block.fillItemGroup(this, items));
        }
    }

    public void addTaggedItems(NonNullList<ItemStack> items) {
        for (Tag<Item> tag : this.items) {
            tag.getAllElements().forEach(item -> item.fillItemGroup(this, items));
        }
    }
}
