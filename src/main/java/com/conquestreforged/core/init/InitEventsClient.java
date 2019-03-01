package com.conquestreforged.core.init;

import com.conquestreforged.core.asset.ResourceProvider;
import com.conquestreforged.core.asset.Resources;
import com.conquestreforged.core.asset.lang.VirtualLang;
import com.conquestreforged.core.asset.pack.PackFinder;
import com.conquestreforged.core.asset.pack.VirtualResourcepack;
import com.conquestreforged.core.block.data.BlockDataRegistry;
import com.conquestreforged.core.item.group.manager.ItemGroupManager;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.ResourcePackList;
import net.minecraft.resources.ResourcePackType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.io.File;
import java.io.IOException;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class InitEventsClient {

    @SubscribeEvent
    public static void models(ModelRegistryEvent event) {
        Init.runStage(Stage.MODEL);
    }

    @SubscribeEvent
    public static void client(FMLClientSetupEvent event) {
        Resources.register(ResourcePackType.CLIENT_RESOURCES, new ResourceProvider() {
            @Override
            public ResourcePackList getPackList() {
                return Minecraft.getInstance().getResourcePackList();
            }

            @Override
            public IResourceManager getResourceManager() {
                return Minecraft.getInstance().getResourceManager();
            }
        });

        // init client virtual resources (assets)
        BlockDataRegistry.getNamespaces().forEach(namespace -> {
            VirtualResourcepack.Builder builder = VirtualResourcepack.builder(namespace).type(ResourcePackType.CLIENT_RESOURCES);
            BlockDataRegistry.getData(namespace).forEach(data -> data.addClientResources(builder));
            builder.add(new VirtualLang(namespace));
            builder.build();
        });

        PackFinder.getInstance(ResourcePackType.CLIENT_RESOURCES).register();

        // init ItemGroup manager
        ItemGroupManager.getInstance().init();

        // run client init tasks
        Init.runStage(Stage.CLIENT);
    }
}
