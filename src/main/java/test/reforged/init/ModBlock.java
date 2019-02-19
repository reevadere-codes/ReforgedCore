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

        registerOldSchool();
    }

    private static void registerOldSchool() {
        VanillaProps.glass()
                .manual() // bypass generation of virtual json assets for this block
                .name("test_block")
                .register(TypeList.of(Block.class));
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
                .texture("*", "block/stone_bricks")
                .register(types);

        VanillaProps.bricks()
                .name("red_stone_bricks", "red_stone_brick")
                .texture("*", "block/red_stone_bricks")
                .register(types);
    }

    private static void registerPlank(TypeList types) {
        VanillaProps.planks()
                .name("oak_planks", "oak_plank")
                .texture("*", "minecraft:block/oak_planks")
                .register(types);

        VanillaProps.planks()
                .name("birch_planks", "birch_plank")
                .texture("*", "minecraft:block/birch_planks")
                .register(types);
    }
}
