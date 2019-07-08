package com.conquestreforged.core.block.standard.tudor;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import com.conquestreforged.core.block.properties.Waterloggable;
import com.conquestreforged.core.block.standard.VerticalCorner;
import net.minecraft.util.Direction;

@Assets(
        state = @State(name = "%s_vertical_corner", template = "parent_vertical_corner_tudor"),
        item = @Model(name = "item/%s_vertical_corner", parent = "block/%s_vertical_corner", template = "item/parent_vertical_corner_tudor"),
        block = {
                @Model(name = "block/%s_vertical_corner", template = "block/parent_vertical_corner_tudor"),
        }
)
public class TudorVerticalCorner extends VerticalCorner implements Waterloggable {

    public TudorVerticalCorner(Properties properties) {
        super(properties);
        this.setDefaultState((this.stateContainer.getBaseState()).with(DIRECTION, Direction.NORTH).with(WATERLOGGED, false));

    }
}
