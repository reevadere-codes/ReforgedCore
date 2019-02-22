package com.conquestreforged.core.item.group.manager;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class ItemGroupWrapper extends ItemGroup {

    private final ItemGroup group;

    ItemGroupWrapper(ItemGroup group) {
        super(-1, group.getTabLabel());
        this.group = group;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ItemStack createIcon() {
        return group.createIcon();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public String getBackgroundImageName() {
        return group.getBackgroundImageName();
    }

    @Override
    public ItemGroup setBackgroundImageName(String texture) {
        return group.setBackgroundImageName(texture);
    }

    @Override
    public ItemGroup func_199783_b(String p_199783_1_) {
        return group.func_199783_b(p_199783_1_);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean drawInForegroundOfTab() {
        return group.drawInForegroundOfTab();
    }

    @Override
    public ItemGroup setNoTitle() {
        return group.setNoTitle();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean hasScrollbar() {
        return group.hasScrollbar();
    }

    @Override
    public ItemGroup setNoScrollbar() {
        return group.setNoScrollbar();
    }

    @Override
    public EnumEnchantmentType[] getRelevantEnchantmentTypes() {
        return group.getRelevantEnchantmentTypes();
    }

    @Override
    public ItemGroup setRelevantEnchantmentTypes(EnumEnchantmentType... types) {
        return group.setRelevantEnchantmentTypes(types);
    }

    @Override
    public boolean hasRelevantEnchantmentType(@Nullable EnumEnchantmentType enchantmentType) {
        return group.hasRelevantEnchantmentType(enchantmentType);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void fill(NonNullList<ItemStack> items) {
        if (group == ItemGroup.HOTBAR) {
            return;
        }
        group.fill(items);
    }

    @Override
    public boolean hasSearchBar() {
        return group.hasSearchBar();
    }

    @Override
    public int getSearchbarWidth() {
        return group.getSearchbarWidth();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ResourceLocation getBackgroundImage() {
        return group.getBackgroundImage();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ResourceLocation getTabsImage() {
        return group.getTabsImage();
    }

    @Override
    public int getLabelColor() {
        return group.getLabelColor();
    }

    @Override
    public int getSlotColor() {
        return group.getSlotColor();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public String getTabLabel() {
        return group.getTabLabel();
    }

    @Override
    public String func_200300_c() {
        return group.func_200300_c();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public String getTranslationKey() {
        return group.getTranslationKey();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ItemStack getIcon() {
        return group.getIcon();
    }
}
