package com.conquestreforged.core.init;

import com.conquestreforged.core.asset.lang.VirtualLang;
import com.conquestreforged.core.asset.pack.PackFinder;
import com.conquestreforged.core.asset.pack.VirtualResourcepack;
import com.conquestreforged.core.block.data.BlockDataRegistry;
import com.conquestreforged.core.item.group.manager.ItemGroupManager;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class InitEventsClient {

    @SubscribeEvent
    public static void models(ModelRegistryEvent event) {
        Init.runStage(Stage.MODEL);
    }

    @SubscribeEvent
    public static void client(FMLClientSetupEvent event) {
        Init.runStage(Stage.CLIENT);
        Init.runStage(Stage.RESOURCE);
        Init.runStage(Stage.RENDER);
    }
}
