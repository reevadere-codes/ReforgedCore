package com.conquestreforged.core.client.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Consumer;

public class InGameScrollHandler {

    public static void listen(EntityPlayer player, Consumer<Integer> consumer) {
        new Hook(player, consumer);
    }

    public static void detach(EntityPlayer player) {
        InventoryPlayer inventory = player.inventory;
        if (inventory instanceof Hook) {
            ((Hook) inventory).detach();
        }
    }

    private static class Hook extends InventoryPlayer {

        private final InventoryPlayer original;
        private final Consumer<Integer> callback;

        private Hook(EntityPlayer player, Consumer<Integer> callback) {
            super(player);
            this.original = player.inventory;
            this.callback = callback;
            player.inventory = this;
        }

        private void detach() {
            player.inventory = original;
        }

        @OnlyIn(Dist.CLIENT)
        public void changeCurrentItem(double direction) {
            int delta = direction > 0 ? 1 : -1;
            callback.accept(delta);
        }
    }
}
