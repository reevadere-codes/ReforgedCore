package com.conquestreforged.core.block.props.prop2;

import com.conquestreforged.core.block.props.BlockName;
import com.conquestreforged.core.block.props.Textures;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemGroup;

public class PropsBuilder extends PropsData {

    public PropsBuilder name(BlockName val) {
        name = val;
        return this;
    }

    public PropsBuilder base(IBlockState val) {
        base = val;
        return this;
    }

    public PropsBuilder material(Material val) {
        material = val;
        return this;
    }

    public PropsBuilder color(MaterialColor val) {
        color = val;
        return this;
    }

    public PropsBuilder dyeColor(EnumDyeColor val) {
        dyeColor = val;
        return this;
    }

    public PropsBuilder sound(SoundType val) {
        sound = val;
        return this;
    }

    public PropsBuilder group(ItemGroup val) {
        group = val;
        return this;
    }

    public PropsBuilder light(int val) {
        light = val;
        return this;
    }

    public PropsBuilder resistance(float val) {
        resistance = val;
        return this;
    }

    public PropsBuilder hardness(float val) {
        hardness = val;
        return this;
    }

    public PropsBuilder slipperiness(float val) {
        slipperiness = val;
        return this;
    }

    public PropsBuilder randomTick(boolean val) {
        randomTick = val;
        return this;
    }

    public PropsBuilder variableOpacity(boolean val) {
        variableOpacity = val;
        return this;
    }

    public PropsBuilder blocksMovement(boolean val) {
        blocksMovement = val;
        return this;
    }

    public PropsBuilder floats(boolean val) {
        floats = val;
        return this;
    }

    public PropsBuilder textures(Textures.Builder val) {
        textures = val;
        return this;
    }

    public PropsBuilder manual(boolean val) {
        manual = val;
        return this;
    }
}
