package com.conquestreforged.core.init;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class InitEventsServer {

    @SubscribeEvent
    public static void starting(FMLServerStartingEvent event) {

    }

    @SubscribeEvent
    public static void started(FMLServerStartedEvent event) {

    }

    @SubscribeEvent
    public static void stopping(FMLServerStoppingEvent event) {

    }
}
