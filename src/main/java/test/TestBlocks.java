package test;

import com.conquestreforged.core.block.builder.VanillaProps;
import com.conquestreforged.core.block.factory.TypeList;
import com.conquestreforged.core.block.standard.*;
import com.conquestreforged.core.util.Log;
import com.google.common.reflect.ClassPath;
import net.minecraft.block.Block;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class TestBlocks {

    private static TypeList getAllTypes() throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        List<Class<? extends Block>> types = ClassPath.from(classLoader)
                .getTopLevelClasses(Cube.class.getPackage().getName())
                .stream()
                .map(ClassPath.ClassInfo::load)
                .filter(Block.class::isAssignableFrom)
                .<Class<? extends Block>>map(c -> c.asSubclass(Block.class))
                .collect(Collectors.toList());
        Log.info("{}", types);
        return TypeList.of(types);
    }

    static void init() {
        TypeList types = TypeList.of(Cube.class, Slab.class, Stairs.class, Wall.class, Fence.class, FenceGate.class);
        VanillaProps.bricks()
                .group(TestGroups.REFINED_STONE)
                .name("test_bricks", "test_brick")
                .texture("minecraft:bricks")
                .register(types);
    }
}
