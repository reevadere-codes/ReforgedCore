package com.conquestreforged.core.init;

import com.conquestreforged.core.asset.ResourceProvider;
import com.conquestreforged.core.asset.Resources;
import com.conquestreforged.core.asset.pack.PackFinder;
import com.conquestreforged.core.util.Log;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.ResourcePackList;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;

import java.io.File;
import java.io.IOException;

@Mod.EventBusSubscriber
public class InitEventsServer {

    @SubscribeEvent
    public static void starting(FMLServerAboutToStartEvent event) {
        Log.info("server init");
        Resources.register(ResourcePackType.SERVER_DATA, new ResourceProvider() {

            private final MinecraftServer server = event.getServer();

            @Override
            public ResourcePackList getPackList() {
                return server.getResourcePacks();
            }

            @Override
            public IResourceManager getResourceManager() {
                return server.getResourceManager();
            }
        });

        PackFinder.getInstance(ResourcePackType.SERVER_DATA).register();
    }

    @SubscribeEvent
    public static void started(FMLServerStartedEvent event) {
        Log.info("server started");
        PackFinder.iterate((type, pack) -> {
            try {
                pack.exportPretty(new File("export"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @SubscribeEvent
    public static void stopping(FMLServerStoppingEvent event) {
        Log.info("server stopping");
    }
}
