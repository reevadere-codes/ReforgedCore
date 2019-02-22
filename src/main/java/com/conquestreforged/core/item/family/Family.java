package com.conquestreforged.core.item.family;

import com.conquestreforged.core.util.Optional;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class Family<T> implements Optional {

    private final List<T> members;
    private final ItemGroup group;
    private final Comparator<T> order;

    public Family(ItemGroup group, List<T> members) {
        this(group, (t1, t2) -> 0, members);
    }

    public Family(ItemGroup group, Comparator<T> order, List<T> members) {
        this.group = group;
        this.order = order;
        this.members = members;
    }

    protected abstract T emptyValue();

    protected abstract void addItem(ItemGroup group, NonNullList<ItemStack> list, T item);

    public ItemGroup getGroup() {
        return group;
    }

    public T getRoot() {
        if (members.isEmpty()) {
            return emptyValue();
        }
        return members.get(0);
    }

    public List<T> getMembers() {
        return new ArrayList<>(members);
    }

    public void add(T member) {
        if (members.contains(member)) {
            return;
        }

        members.add(member);

        if (order == null) {
            return;
        }

        members.sort(order);
    }

    public void addAllItems(ItemGroup group, NonNullList<ItemStack> list) {
        if (group == ItemGroup.SEARCH || group == this.group) {
            for (T t : members) {
                addItem(group, list, t);
            }
        }
    }

    public void addRootItem(ItemGroup group, NonNullList<ItemStack> list) {
        if (group == ItemGroup.SEARCH || group == this.group) {
            addItem(group, list, getRoot());
        }
    }

    public interface Filler {

        void fill(Family family, ItemGroup group, NonNullList<ItemStack> items);
    }
}
