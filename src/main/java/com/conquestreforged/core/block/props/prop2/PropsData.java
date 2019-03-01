package com.conquestreforged.core.block.props.prop2;

import com.conquestreforged.core.block.props.BlockName;
import com.conquestreforged.core.block.props.Textures;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemGroup;

public class PropsData {

    public BlockName name = null;
    public IBlockState base = null;
    public Material material = null;
    public MaterialColor color = null;
    public EnumDyeColor dyeColor = null;
    public SoundType sound = null;
    public ItemGroup group = ItemGroup.SEARCH;
    public int light = Integer.MAX_VALUE;
    public float resistance = Float.MAX_VALUE;
    public float hardness = Float.MAX_VALUE;
    public float slipperiness = Float.MAX_VALUE;
    public boolean randomTick = false;
    public boolean variableOpacity = false;
    public boolean blocksMovement = true;
    public boolean floats = true;
    public Textures.Builder textures;

    public boolean manual = false;
}
