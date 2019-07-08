package com.conquestreforged.core.block.standard.tudor;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import com.conquestreforged.core.block.properties.Waterloggable;
import com.conquestreforged.core.block.standard.VerticalCornerThin;
import net.minecraft.block.Block;
import net.minecraft.util.Direction;

@Assets(
        state = @State(name = "%s_thin_vertical_corner", template = "parent_thin_vertical_corner_tudor"),
        item = @Model(name = "item/%s_thin_vertical_corner", parent = "block/%s_thin_vertical_corner", template = "item/parent_thin_vertical_corner_tudor"),
        block = {
                @Model(name = "block/%s_thin_vertical_corner", template = "block/parent_thin_vertical_corner_tudor"),
        }
)
public class TudorVerticalCornerThin extends VerticalCornerThin implements Waterloggable {

    public TudorVerticalCornerThin(Block.Properties properties) {
        super(properties);
        this.setDefaultState((this.stateContainer.getBaseState()).with(DIRECTION, Direction.NORTH).with(WATERLOGGED, false));
    }
}
