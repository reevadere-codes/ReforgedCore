package com.conquestreforged.core.block.props.prop2;

import com.conquestreforged.core.block.props.BlockName;
import com.conquestreforged.core.block.props.Textures;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemGroup;

public interface PropsView {
    
    default PropsData self() {
        return (PropsData) this;
    }

    default BlockName getName() {
        return self().name;
    }

    default IBlockState getBase() {
        return self().base;
    }

    default Material getMaterial() {
        return self().material;
    }

    default MaterialColor getColor() {
        return self().color;
    }

    default EnumDyeColor getDyeColor() {
        return self().dyeColor;
    }

    default SoundType getSound() {
        return self().sound;
    }

    default ItemGroup getGroup() {
        return self().group;
    }

    default int getLight() {
        return self().light;
    }

    default float getResistance() {
        return self().resistance;
    }

    default float getHardness() {
        return self().hardness;
    }

    default float getSlipperiness() {
        return self().slipperiness;
    }

    default boolean isRandomTick() {
        return self().randomTick;
    }

    default boolean isVariableOpacity() {
        return self().variableOpacity;
    }

    default boolean isBlocksMovement() {
        return self().blocksMovement;
    }

    default boolean isFloats() {
        return self().floats;
    }

    default Textures.Builder getTextures() {
        return self().textures;
    }

    default boolean isManual() {
        return self().manual;
    }
}
