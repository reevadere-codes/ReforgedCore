package com.conquestreforged.core.block.shape;

import com.conquestreforged.core.block.data.BlockData;
import net.minecraft.item.crafting.IRecipe;

public interface IRecipeProvider {

    IRecipe build(BlockData data);
}
