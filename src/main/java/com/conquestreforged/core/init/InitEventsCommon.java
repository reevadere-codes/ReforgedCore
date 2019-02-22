package com.conquestreforged.core.init;

import com.conquestreforged.core.block.data.BlockDataRegistry;
import com.conquestreforged.core.util.Cache;
import com.conquestreforged.core.util.Log;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class InitEventsCommon {

    @SubscribeEvent
    public static void blocks(RegistryEvent.Register<Block> event) {
        Init.runStage(Stage.BLOCK);
        BlockDataRegistry.BLOCK_DATA.forEach(data -> event.getRegistry().register(data.getBlock()));
    }

    @SubscribeEvent
    public static void items(RegistryEvent.Register<Item> event) {
        Init.runStage(Stage.ITEM);
        BlockDataRegistry.BLOCK_DATA.forEach(data -> event.getRegistry().register(data.getItem()));
    }

    @SubscribeEvent
    public static void common(FMLCommonSetupEvent event) {
        Init.runStage(Stage.COMMON);
    }

    @SubscribeEvent
    public static void complete(FMLLoadCompleteEvent event) {
        Init.runStage(Stage.COMPLETE);
        Log.debug("clearing caches");
        Cache.clearAll();
        BlockDataRegistry.clear();
    }
}
