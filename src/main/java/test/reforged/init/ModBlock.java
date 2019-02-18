package test.reforged.init;

import com.conquestreforged.core.block.*;
import com.conquestreforged.core.block.factory.TypeList;
import com.conquestreforged.core.block.props.VanillaProps;

public class ModBlock {

    public static void register() {
        TypeList standardShapes = TypeList.of(Block.class, Slab.class, Stairs.class, Wall.class, Fence.class, FenceGate.class);
        registerStone(standardShapes);
        registerBrick(standardShapes);
        registerPlank(standardShapes);
    }

    private static void registerStone(TypeList types) {
        VanillaProps.stone()
                .name("andesite")
                .texture("*", "block/andesite")
                .register(types);

        VanillaProps.stone()
                .name("diorite")
                .texture("*", "block/diorite")
                .register(types);

        VanillaProps.stone()
                .name("granite")
                .texture("*", "block/granite")
                .register(types);
    }

    private static void registerBrick(TypeList types) {
        VanillaProps.bricks()
                .name("stone_bricks", "stone_brick")
                .register(types);

        VanillaProps.bricks()
                .name("red_stone_bricks", "red_stone_brick")
                .register(types);
    }

    private static void registerPlank(TypeList types) {
        VanillaProps.planks()
                .name("oak_planks", "oak_plank")
                .texture("*", "minecraft:oak_planks")
                .register(types);

        VanillaProps.planks()
                .name("birch_planks", "birch_plank")
                .texture("*", "minecraft:birch_plank")
                .register(types);
    }
}
