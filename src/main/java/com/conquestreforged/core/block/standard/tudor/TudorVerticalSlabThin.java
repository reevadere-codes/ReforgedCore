package com.conquestreforged.core.block.standard.tudor;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import com.conquestreforged.core.block.properties.Waterloggable;
import com.conquestreforged.core.block.standard.VerticalSlabThin;
import net.minecraft.block.Block;
import net.minecraft.util.Direction;

@Assets(
        state = @State(name = "%s_thin_vertical_slab", template = "parent_vertical_slab_thin_tudor"),
        item = @Model(name = "item/%s_vertical_slab_thin", parent = "block/%s_vertical_slab_thin", template = "item/parent_vertical_slab_thin_tudor"),
        block = {
                @Model(name = "block/%s_vertical_slab_thin", template = "block/parent_vertical_slab_thin_tudor"),
        }
)
public class TudorVerticalSlabThin extends VerticalSlabThin implements Waterloggable {

    public TudorVerticalSlabThin(Block.Properties properties) {
        super(properties);
        this.setDefaultState((this.stateContainer.getBaseState()).with(DIRECTION, Direction.NORTH).with(WATERLOGGED, false));

    }
}
