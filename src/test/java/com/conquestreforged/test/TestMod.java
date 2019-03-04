package com.conquestreforged.test;

import com.conquestreforged.core.init.Init;
import com.conquestreforged.core.init.Stage;
import com.conquestreforged.test.init.ModBlock;
import net.minecraftforge.fml.common.Mod;

@Mod("testmod")
public class TestMod {

    public TestMod() {
        Init.register(Stage.BLOCK, ModBlock::init);
    }
}
