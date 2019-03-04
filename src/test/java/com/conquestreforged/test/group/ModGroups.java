package com.conquestreforged.test.group;

import com.conquestreforged.core.item.family.FamilyGroup;
import net.minecraft.item.ItemGroup;

public class ModGroups {

    public static final ItemGroup RAW_STONE = new FamilyGroup(0, "raw_stone", "minecraft:andesite");
    public static final ItemGroup RAW_WOOD = new FamilyGroup(1, "raw_wood", "minecraft:oak_log");
    public static final ItemGroup RAW_ORE = new FamilyGroup(2, "raw_ore", "minecraft:diamond_ore");
    public static final ItemGroup PLANTS = new FamilyGroup(3, "plants", "minecraft:rose_bush");
    public static final ItemGroup REFINED_STONE = new FamilyGroup(4, "refined_stone", "minecraft:stone_bricks");
    public static final ItemGroup REFINED_WOOD = new FamilyGroup(5, "refined_wood", "minecraft:oak_planks");
    public static final ItemGroup REFINED_ORE = new FamilyGroup(6, "refined_ore", "minecraft:diamond_block");
    public static final ItemGroup GLASS = new FamilyGroup(7, "glass", "minecraft:glass");
    public static final ItemGroup FABRIC = new FamilyGroup(8, "fabric", "minecraft:white_wool");
    public static final ItemGroup UTILITIES = new FamilyGroup(9, "utilities", "minecraft:crafting_table");

}
