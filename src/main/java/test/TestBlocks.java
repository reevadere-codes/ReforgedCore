package test;

import com.conquestreforged.core.block.Anvil;
import com.conquestreforged.core.block.builder.VanillaProps;
import com.conquestreforged.core.block.factory.TypeList;
import com.conquestreforged.core.block.standard.Cube;
import com.conquestreforged.core.util.Provider;
import com.google.common.reflect.ClassPath;
import net.minecraft.block.Block;
import net.minecraft.block.trees.OakTree;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class TestBlocks {

    static void init() {
        TypeList blockTypes = scanBlockTypes();

        VanillaProps.bricks()
                .group(TestGroups.REFINED_STONE)
                .name("example_bricks", "example_brick")
                .texture("minecraft:bricks")
                .with("crop", Provider.item("minecraft:beetroot"))
                .with("fruit", Provider.item("minecraft:beetroot"))
                .with("seeds", Provider.item("minecraft:beetroot_seeds"))
                .with("sapling", Provider.item("minecraft:oak_sapling"))
                .with("tree", new OakTree())
                .register(blockTypes);
    }

    private static TypeList scanBlockTypes() {
        List<Class<? extends Block>> types = new LinkedList<>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        collectTypes(classLoader, Cube.class, types);
        collectTypes(classLoader, Anvil.class, types);
        return TypeList.of(types);
    }

    @SuppressWarnings("UnstableApiUsage")
    private static void collectTypes(ClassLoader classLoader, Class classPackage, List<Class<? extends Block>> types) {
        try {
            ClassPath.from(classLoader).getTopLevelClasses(classPackage.getPackage().getName()).stream()
                    .map(TestBlocks::load)
                    .filter(Objects::nonNull)
                    .forEach(types::add);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @SuppressWarnings("UnstableApiUsage")
    private static Class<? extends Block> load(ClassPath.ClassInfo classInfo) {
        try {
            Class<?> c = Class.forName(classInfo.getName());
            if (Block.class.isAssignableFrom(c)) {
                return c.asSubclass(Block.class);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
