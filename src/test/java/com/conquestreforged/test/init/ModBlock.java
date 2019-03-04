package com.conquestreforged.test.init;

import com.conquestreforged.test.group.ModGroups;
import com.conquestreforged.core.block.*;
import com.conquestreforged.core.block.factory.TypeList;
import com.conquestreforged.core.block.props.VanillaProps;

public class ModBlock {

    public static void init() {
        TypeList shapes = TypeList.of(Cube.class, Slab.class, Stairs.class, Wall.class, Fence.class, FenceGate.class);
        stone(shapes);
        brick(shapes);
        plank(shapes);
    }

    private static void stone(TypeList types) {
        VanillaProps.stone()
                .group(ModGroups.RAW_STONE)
                .name("andesite")
                .texture("andesite")
                .register(types);

        VanillaProps.stone()
                .group(ModGroups.RAW_STONE)
                .name("diorite")
                .texture("diorite")
                .register(types);

        VanillaProps.stone()
                .group(ModGroups.RAW_STONE)
                .name("granite")
                .texture("granite")
                .register(types);
    }

    private static void brick(TypeList types) {
        VanillaProps.bricks()
                .group(ModGroups.REFINED_STONE)
                .name("stone_bricks", "stone_brick")
                .texture("stone_bricks")
                .register(types);

        VanillaProps.bricks()
                .group(ModGroups.REFINED_STONE)
                .name("red_stone_bricks", "red_stone_brick")
                .texture("red_stone_bricks")
                .register(types);
    }

    private static void plank(TypeList types) {
        VanillaProps.planks()
                .group(ModGroups.REFINED_WOOD)
                .name("oak_planks", "oak_plank")
                .texture("minecraft:oak_planks")
                .register(types);

        VanillaProps.planks()
                .group(ModGroups.REFINED_WOOD)
                .name("birch_planks", "birch_plank")
                .texture("minecraft:birch_planks")
                .register(types);
    }
}
