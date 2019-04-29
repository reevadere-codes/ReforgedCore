package com.conquestreforged.core.init;

import com.conquestreforged.core.asset.lang.VirtualLang;
import com.conquestreforged.core.asset.pack.PackFinder;
import com.conquestreforged.core.asset.pack.VirtualResourcepack;
import com.conquestreforged.core.block.data.BlockDataRegistry;
import com.conquestreforged.core.item.group.manager.ItemGroupManager;
import com.conquestreforged.core.proxy.Proxies;
import com.conquestreforged.core.proxy.Side;
import com.conquestreforged.core.proxy.impl.ClientProxy;
import net.minecraft.resources.ResourcePackType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class InitEventsClient {

    @SubscribeEvent
    public static void models(ModelRegistryEvent event) {
        Init.runStage(Stage.MODEL);
    }

    @SubscribeEvent
    public static void client(FMLClientSetupEvent event) {
        Proxies.set(Side.CLIENT, new ClientProxy());
        Init.register(Stage.COMPLETE, Side.CLIENT.getProxy()::registerListeners);

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
