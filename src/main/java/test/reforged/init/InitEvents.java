package test.reforged.init;

import com.conquestreforged.core.registry.BlockDataRegistry;
import com.conquestreforged.core.resource.PackFinder;
import com.conquestreforged.core.util.Cache;
import com.conquestreforged.core.util.Log;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class InitEvents {

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        Log.info("registering blocks");
        ModBlock.register();
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        Log.info("registering items");
        ModItem.register();
    }

    @SubscribeEvent
    public static void registerResources(FMLClientSetupEvent event) {
        Log.info("registering resources");
        ModResource.register();
    }

    @SubscribeEvent
    public static void registerPackFinder(FMLClientSetupEvent event) {
        Log.info("registering pack finder");
        Minecraft.getInstance().getResourcePackList().addPackFinder(PackFinder.getInstance());
    }

    @SubscribeEvent
    public static void test(FMLLoadCompleteEvent event) {
        Log.info("testing blocks");
        Test.blocks();

        Log.info("testing resources");
        Test.resources();

        Log.info("clearing caches");
        Cache.clearAll();
        BlockDataRegistry.clear();
    }
}
