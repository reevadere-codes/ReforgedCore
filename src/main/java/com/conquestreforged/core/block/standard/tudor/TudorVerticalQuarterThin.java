package com.conquestreforged.core.block.standard.tudor;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import com.conquestreforged.core.block.properties.Waterloggable;
import net.minecraft.block.Block;
import net.minecraft.util.Direction;

@Assets(
        state = @State(name = "%s_thin_vertical_quarter", template = "parent_vertical_quarter_thin_tudor"),
        item = @Model(name = "item/%s_vertical_quarter_thin", parent = "block/%s_vertical_quarter_thin", template = "item/parent_vertical_quarter_thin_tudor"),
        block = {
                @Model(name = "block/%s_vertical_quarter_thin", template = "block/parent_vertical_quarter_thin_tudor"),
        }
)
public class TudorVerticalQuarterThin extends com.conquestreforged.core.block.standard.VerticalQuarterThin implements Waterloggable {

    public TudorVerticalQuarterThin(Block.Properties properties) {
        super(properties);
        this.setDefaultState((this.stateContainer.getBaseState()).with(DIRECTION, Direction.NORTH).with(WATERLOGGED, false));

    }
}
