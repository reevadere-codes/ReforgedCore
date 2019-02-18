package test.reforged;

import com.conquestreforged.core.util.Log;
import net.minecraftforge.fml.common.Mod;

@Mod("testmod")
public class TestMod {

    public TestMod() {
        Log.level(Log.DEBUG);
        Log.info("mod init");
    }
}