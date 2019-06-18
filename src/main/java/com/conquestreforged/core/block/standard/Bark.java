package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import net.minecraft.block.RotatedPillarBlock;

@Assets(
        state = @State(name = "%s_bark", template = "acacia_log"),
        item = @Model(name = "item/%s_bark", parent = "block/%s_bark", template = "item/acacia_log"),
        block = {
                @Model(name = "block/%s_bark", template = "block/acacia_log")
        }
)
public class Bark extends RotatedPillarBlock {
    public Bark(Properties properties) {
        super(properties);
    }
}