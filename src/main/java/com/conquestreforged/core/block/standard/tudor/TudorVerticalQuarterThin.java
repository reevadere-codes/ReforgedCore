package com.conquestreforged.core.block.standard.tudor;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import com.conquestreforged.core.block.properties.Waterloggable;
import net.minecraft.block.Block;
import net.minecraft.util.Direction;

@Assets(
        state = @State(name = "%s_thin_vertical_quarter", template = "parent_thin_vertical_quarter_tudor"),
        item = @Model(name = "item/%s_thin_vertical_quarter", parent = "block/%s_thin_vertical_quarter", template = "item/parent_thin_vertical_quarter_tudor"),
        block = {
                @Model(name = "block/%s_thin_vertical_quarter", template = "block/parent_thin_vertical_quarter_tudor"),
        }
)
public class TudorVerticalQuarterThin extends com.conquestreforged.core.block.standard.VerticalQuarterThin implements Waterloggable {

    public TudorVerticalQuarterThin(Block.Properties properties) {
        super(properties);
        this.setDefaultState((this.stateContainer.getBaseState()).with(DIRECTION, Direction.NORTH).with(WATERLOGGED, false));

    }
}
