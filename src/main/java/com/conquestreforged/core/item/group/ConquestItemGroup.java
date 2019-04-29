package com.conquestreforged.core.item.group;

import com.conquestreforged.core.item.group.manager.ItemGroupManager;
import net.minecraft.item.ItemGroup;

public abstract class ConquestItemGroup extends ItemGroup {

    private final int index;

    public ConquestItemGroup(int index, String label) {
        super(-1, label);
        this.index = index;
        ItemGroupManager.getInstance().register(this);
    }

    public int getOriginalIndex() {
        return index;
    }
}
