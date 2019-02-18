package test.reforged;

import com.conquestreforged.core.util.Log;
import net.minecraftforge.fml.common.Mod;

@Mod(TestMod.MOD_ID)
public class TestMod {

    public static final String MOD_ID = "testmod";

    public TestMod() {
        Log.level(Log.DEBUG);
        Log.info("mod init");
    }
}