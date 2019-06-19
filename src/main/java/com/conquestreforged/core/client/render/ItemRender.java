package com.conquestreforged.core.client.render;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;

public class ItemRender {

    public static void render(ItemStack stack, int left, int top) {
        render(stack, left, top, 1F, 1F);
    }

    public static void render(ItemStack stack, int left, int top, float brightness, float alpha) {
        render(stack, left, top, brightness, alpha, 1F);
    }

    public static void render(ItemStack stack, int left, int top, float brightness, float alpha, float scale) {
        RenderHelper.enableGUIStandardItemLighting();
//        GlStateManager.lightModelfv(2899, RenderHelper.setColorBuffer(0.6F, 0.6F, 0.6F, alpha));
        GlStateManager.pushMatrix();
        GlStateManager.translated(left, top, 0);
        GlStateManager.scaled(scale, scale, 1F);
        Minecraft.getInstance().getItemRenderer().renderItemIntoGUI(stack, 0, 0);
        Minecraft.getInstance().getItemRenderer().renderItemOverlayIntoGUI(Minecraft.getInstance().fontRenderer, stack, 0, 0, null);
        GlStateManager.popMatrix();
//        RenderHelper.disableStandardItemLighting();
    }
}
