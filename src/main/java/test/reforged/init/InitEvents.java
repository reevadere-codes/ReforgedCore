package test.reforged.init;

import com.conquestreforged.core.registry.BlockDataRegistry;
import com.conquestreforged.core.asset.pack.PackFinder;
import com.conquestreforged.core.util.Cache;
import com.conquestreforged.core.util.Context;
import com.conquestreforged.core.util.Log;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class InitEvents {

    // ..Register<Block> - first
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        // ie the folder where our assets are stored
        Context.getInstance().setNamespace("conquest");

        Log.info("registering blocks");
        ModBlock.register();
    }

    // ..Register<Item> - after Blocks have been registered
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        Log.info("registering items");
        ModItem.register();
    }

    // FMLCommonSetupEvent - after RegistryEvents
    @SubscribeEvent
    public static void registerResources(FMLCommonSetupEvent event) {
        Log.info("registering resources");
        ModResource.register();
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        Log.info("registering models");
        ModRenderers.register();
    }

    // FMLClientSetupEvent - after FMLCommonSetupEvent
    @SubscribeEvent
    public static void registerPackFinder(FMLClientSetupEvent event) {
        Log.info("registering pack finder");
        Minecraft.getInstance().getResourcePackList().addPackFinder(PackFinder.getInstance());
    }

    // FMLLoadCompleteEvent - last
    @SubscribeEvent
    public static void test(FMLLoadCompleteEvent event) {
        Log.debug("testing blocks");
        Test.blocks();

        Log.debug("testing resources");
        Test.resources();

        Log.debug("clearing caches");
        Cache.clearAll();
        BlockDataRegistry.clear();
    }
}
