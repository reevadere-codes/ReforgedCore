package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;

@Assets(
        state = @State(name = "%s_arch_segment", template = "parent_segmental_arch"),
        item = @Model(name = "item/%s_arch_segment", parent = "block/%s_arch_segment_one", template = "item/parent_segmental_arch"),
        block = {
                @Model(name = "block/%s_arch_segment_one", template = "block/parent_segmental_arch_one"),
                @Model(name = "block/%s_arch_segment_two", template = "block/parent_segmental_arch_two"),
                @Model(name = "block/%s_arch_segment_three", template = "block/parent_segmental_arch_three"),
                @Model(name = "block/%s_arch_segment_three_top", template = "block/parent_segmental_arch_three_top"),
        }
)
public class ArchSegment extends ArchRound {

    public ArchSegment(Properties properties) {
        super(properties);
    }

}