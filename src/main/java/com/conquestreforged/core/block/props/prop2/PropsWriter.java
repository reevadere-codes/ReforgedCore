package com.conquestreforged.core.block.props.prop2;

import com.conquestreforged.core.block.props.BlockName;
import com.conquestreforged.core.block.props.Textures;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemGroup;

public interface PropsWriter {

    default PropsData data() {
        return (PropsData) this;
    }

    default PropsWriter setName(BlockName name) {
        data().name = name;
        return this;
    }

    default PropsWriter setBase(IBlockState base) {
        data().base = base;
        return this;
    }

    default PropsWriter setMaterial(Material material) {
        data().material = material;
        return this;
    }

    default PropsWriter setColor(MaterialColor color) {
        data().color = color;
        return this;
    }

    default PropsWriter setDyeColor(EnumDyeColor dyeColor) {
        data().dyeColor = dyeColor;
        return this;
    }

    default PropsWriter setSound(SoundType sound) {
        data().sound = sound;
        return this;
    }

    default PropsWriter setGroup(ItemGroup group) {
        data().group = group;
        return this;
    }

    default PropsWriter setLight(int light) {
        data().light = light;
        return this;
    }

    default PropsWriter setResistance(float resistance) {
        data().resistance = resistance;
        return this;
    }

    default PropsWriter setHardness(float hardness) {
        data().hardness = hardness;
        return this;
    }

    default PropsWriter setSlipperiness(float slipperiness) {
        data().slipperiness = slipperiness;
        return this;
    }

    default PropsWriter setRandomTick(boolean randomTick) {
        data().randomTick = randomTick;
        return this;
    }

    default PropsWriter setVariableOpacity(boolean variableOpacity) {
        data().variableOpacity = variableOpacity;
        return this;
    }

    default PropsWriter setBlocksMovement(boolean blocksMovement) {
        data().blocksMovement = blocksMovement;
        return this;
    }

    default PropsWriter setFloats(boolean floats) {
        data().floats = floats;
        return this;
    }

    default PropsWriter setTextures(Textures.Builder textures) {
        data().textures = textures;
        return this;
    }

    default PropsWriter setManual(boolean manual) {
        data().manual = manual;
        return this;
    }
}
