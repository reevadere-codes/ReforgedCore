package com.conquestreforged.core.item.family;

import com.conquestreforged.core.util.OptionalValue;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public abstract class Family<T> implements OptionalValue {

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

    public Optional<T> getMember(int index) {
        if (index < members.size()) {
            return Optional.ofNullable(members.get(index));
        }
        return Optional.empty();
    }

    public Optional<T> getMember(Class<? extends T> type) {
        return members.stream().filter(t -> t.getClass() == type).findFirst();
    }

    public List<T> getMembers() {
        return new ArrayList<>(members);
    }

    public int size() {
        return members.size();
    }

    public int indexOf(T member) {
        return members.indexOf(member);
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
        addAllItems(group, list, TypeFilter.ANY);
    }

    public void addAllItems(ItemGroup group, NonNullList<ItemStack> list, TypeFilter filter) {
        if (group == ItemGroup.SEARCH || group == this.group) {
            for (T t : members) {
                if (filter.test(t)) {
                    addItem(group, list, t);
                }
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
