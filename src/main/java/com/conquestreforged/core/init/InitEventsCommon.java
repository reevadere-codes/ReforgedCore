package com.conquestreforged.core.init;

import com.conquestreforged.core.asset.pack.VirtualResourcepack;
import com.conquestreforged.core.block.data.BlockDataRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.resources.ResourcePackType;
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
        // init common virtual resources (data)
        BlockDataRegistry.getNamespaces().forEach(namespace -> {
            VirtualResourcepack.Builder builder = VirtualResourcepack.builder(namespace).type(ResourcePackType.SERVER_DATA);
            BlockDataRegistry.getData(namespace).forEach(data -> data.addServerResources(builder));
            builder.build();
        });

        // run common init tasks
        Init.runStage(Stage.COMMON);
    }

    @SubscribeEvent
    public static void complete(FMLLoadCompleteEvent event) {
        Init.runStage(Stage.COMPLETE);
    }
}
