package com.conquestreforged.core.block.props;

import net.minecraft.init.Blocks;

public class VanillaProps {

    public static Props stone() {
        return Props.create(Blocks.STONE);
    }

    public static Props bricks() {
        return Props.create(Blocks.BRICKS);
    }

    public static Props glass() {
        return Props.create(Blocks.GLASS);
    }

    public static Props cloth() {
        return Props.create(Blocks.WHITE_WOOL);
    }

    public static Props planks() {
        return Props.create(Blocks.OAK_PLANKS);
    }

    public static Props logs() {
        return Props.create(Blocks.OAK_LOG);
    }

    public static Props grass() {
        return Props.create(Blocks.GRASS_BLOCK);
    }

    public static Props plants() {
        return Props.create(Blocks.GRASS);
    }
}
