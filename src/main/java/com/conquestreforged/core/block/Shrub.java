package com.conquestreforged.core.block;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;

@Assets(
        state = @State(name = "%s", template = "parent_shrub"),
        item = @Model(name = "item/%s", parent = "block/%s_pane_ns", template = "item/parent_round_arch"),
        block = {
                @Model(name = "block/%s_shrub_model_1", template = "block/parent_shrub_model_1"),
                @Model(name = "block/%s_shrub_model_2", template = "block/parent_shrub_model_2"),
                @Model(name = "block/%s_plant_dense_model_3", template = "block/parent_plant_dense_model_3"),
                @Model(name = "block/%s_pane_ns", template = "block/parent_flatpane_ns"),
        }
)
public class Shrub extends Bush {
    public Shrub(Properties properties) {
        super(properties);
    }
}
