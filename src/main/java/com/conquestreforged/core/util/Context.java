package com.conquestreforged.core.util;

import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModLoadingContext;

public class Context {

    public static ModContainer mod() {
        return ModLoadingContext.get().getActiveContainer();
    }

    public static String modId() {
        return mod().getModId();
    }
}
