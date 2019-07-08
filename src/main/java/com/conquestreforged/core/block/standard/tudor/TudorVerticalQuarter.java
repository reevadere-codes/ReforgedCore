package com.conquestreforged.core.block.standard.tudor;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import com.conquestreforged.core.block.properties.Waterloggable;
import com.conquestreforged.core.block.standard.VerticalQuarter;
import net.minecraft.util.Direction;

@Assets(
        state = @State(name = "%s_vertical_quarter", template = "parent_vertical_quarter_tudor"),
        item = @Model(name = "item/%s_vertical_quarter", parent = "block/%s_vertical_quarter", template = "item/parent_vertical_quarter_tudor"),
        block = {
                @Model(name = "block/%s_vertical_quarter", template = "block/parent_vertical_quarter_tudor"),
        }
)
public class TudorVerticalQuarter extends VerticalQuarter implements Waterloggable {

    public TudorVerticalQuarter(Properties properties) {
        super(properties);
        this.setDefaultState((this.stateContainer.getBaseState()).with(HORIZONTAL_FACING, Direction.NORTH).with(WATERLOGGED, false));

    }
}
