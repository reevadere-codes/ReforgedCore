package com.conquestreforged.core.block.standard.tudor;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import com.conquestreforged.core.block.properties.Waterloggable;
import com.conquestreforged.core.block.standard.VerticalSlab;
import net.minecraft.util.Direction;

@Assets(
        state = @State(name = "%s_vertical_slab", template = "parent_vertical_slab_tudor"),
        item = @Model(name = "item/%s_vertical_slab", parent = "block/%s_vertical_slab", template = "item/parent_vertical_slab_tudor"),
        block = {
                @Model(name = "block/%s_vertical_slab", template = "block/parent_vertical_slab_tudor"),
        }
)
public class TudorVerticalSlab extends VerticalSlab implements Waterloggable {

    public TudorVerticalSlab(Properties properties) {
        super(properties);
        this.setDefaultState((this.stateContainer.getBaseState()).with(DIRECTION, Direction.NORTH).with(WATERLOGGED, false));
    }
}
