package test;

import com.conquestreforged.core.init.Init;
import com.conquestreforged.core.init.Stage;
import com.conquestreforged.core.item.family.FamilyGroup;
import com.conquestreforged.core.item.group.manager.GroupType;
import com.conquestreforged.core.item.group.manager.ItemGroupManager;
import com.conquestreforged.core.init.Context;
import com.conquestreforged.core.util.Log;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod("testmod")
public class TestMod {

    public TestMod() {
        Log.level(Log.DEBUG);
        Context.getInstance().setNamespace("testmod");
        Init.register(Stage.BLOCK, TestBlocks::init);
        Init.register(Stage.CLIENT, () -> {
            FamilyGroup.setAddAllItems();
            ItemGroupManager.getInstance().setVisibleItemGroups(GroupType.VANILLA, GroupType.CONQUEST, GroupType.OTHER);
        });
        Init.register(Stage.SERVER_STARTED, () -> ForgeRegistries.BLOCKS.forEach(block -> {
            if (block.getRegistryName().getNamespace().equals("minecraft")) {
                return;
            }
            Log.info("{}", block);
        }));
    }
}
