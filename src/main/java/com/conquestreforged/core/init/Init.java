package com.conquestreforged.core.init;

import com.conquestreforged.core.util.Log;
import net.minecraft.resources.IResourceManager;
import sun.net.ResourceManager;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class Init {

    private static final Init instance = new Init();

    private final Map<Stage, List<Runnable>> tasks = new ConcurrentHashMap<>();

    private Init() {

    }

    public static void register(Stage stage, Runnable runnable) {
        instance.tasks.computeIfAbsent(stage, k -> new ArrayList<>()).add(runnable);
    }

    static void runStage(Stage stage) {
        Log.info("init stage {}", stage);
        for (Runnable runnable : instance.tasks.getOrDefault(stage, Collections.emptyList())) {
            runnable.run();
        }
    }
}
