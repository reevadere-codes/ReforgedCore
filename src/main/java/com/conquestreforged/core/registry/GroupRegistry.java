package com.conquestreforged.core.registry;

import com.google.common.collect.ImmutableList;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupRegistry<T> {

    private final Map<T, List<T>> groups = new HashMap<>();

    public void register(List<T> list) {
        List<T> group = ImmutableList.copyOf(list);
        list.forEach(block -> groups.put(block, group));
    }

    public List<T> getGroup(T value) {
        return groups.getOrDefault(value, Collections.emptyList());
    }
}
