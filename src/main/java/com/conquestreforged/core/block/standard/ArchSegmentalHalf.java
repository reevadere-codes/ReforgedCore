package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;

@Assets(
        state = @State(name = "%s_segmental_arch_half", template = "parent_segmental_arch_half"),
        item = @Model(name = "item/%s_arch_segmental_half", parent = "block/%s_arch_segmental_half_one", template = "item/parent_segmental_arch_half"),
        block = {
                @Model(name = "block/%s_segmental_arch_one_half", template = "block/parent_segmental_arch_one_half"),
                @Model(name = "block/%s_arch_segmental_half_two", template = "block/parent_segmental_arch_half_two"),
                @Model(name = "block/%s_arch_segmental_half_three", template = "block/parent_segmental_arch_half_three"),
                @Model(name = "block/%s_arch_segmental_half_three_top", template = "block/parent_segmental_arch_half_three_top"),
        }
)
public class ArchSegmentalHalf extends ArchRoundHalf {

    public ArchSegmentalHalf(Properties properties) {
        super(properties);
    }

}