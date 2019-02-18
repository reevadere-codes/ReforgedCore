package com.conquestreforged.core.block.props;

import com.conquestreforged.core.block.factory.Factory;
import com.conquestreforged.core.block.factory.InitializationException;
import com.conquestreforged.core.util.Context;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemGroup;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Props implements Factory {

    private BlockName name = null;
    private IBlockState base = null;
    private Material material = null;
    private MaterialColor color = null;
    private EnumDyeColor dyeColor = null;
    private SoundType sound = null;
    private ItemGroup group = ItemGroup.SEARCH;
    private int light = Integer.MAX_VALUE;
    private float resistance = Float.MAX_VALUE;
    private float hardness = Float.MAX_VALUE;
    private float slipperiness = Float.MAX_VALUE;
    private boolean randomTick = false;
    private boolean variableOpacity = false;
    private boolean blocksMovement = true;
    private Textures.Builder textures;

    @Override
    public Props getProps() {
        return this;
    }

    @Override
    public BlockName getName() {
        if (base == null) {
            throw new InitializationException("Block name is null");
        }
        return name;
    }

    @Override
    public IBlockState getParent() throws InitializationException {
        if (base == null) {
            throw new InitializationException("Parent state is null");
        }
        return base;
    }

    public IBlockState block() {
        return base;
    }

    public EnumDyeColor dye() {
        return dyeColor;
    }

    public Textures textures() {
        if (textures == null || textures.isEmpty()) {
            return Textures.NONE;
        }
        return textures.build();
    }

    public ItemGroup group() {
        return group;
    }

    public Props name(String namespace, String plural, String singular) {
        return name(BlockName.of(namespace, plural, singular));
    }

    public Props name(String plural, String singular) {
        return name(Context.getInstance().getNamespace(), plural, singular);
    }

    public Props name(String name) {
        return name(Context.getInstance().getNamespace(), name, name);
    }

    public Props name(BlockName name) {
        this.name = name;
        return this;
    }

    public Props block(IBlockState base) {
        this.base = base;
        return this;
    }

    public Props material(Material material) {
        this.material = material;
        return this;
    }

    public Props color(MaterialColor color) {
        this.color = color;
        return this;
    }

    public Props dye(EnumDyeColor color) {
        this.dyeColor = color;
        return this;
    }

    public Props sound(SoundType sound) {
        this.sound = sound;
        return this;
    }

    public Props light(int light) {
        this.light = light;
        return this;
    }

    public Props strength(double hardness, double resistance) {
        this.hardness = (float) hardness;
        this.resistance = (float) resistance;
        return this;
    }

    public Props slipperiness(double slipperiness) {
        this.slipperiness = (float) slipperiness;
        return this;
    }

    public Props randomTick(boolean randomTick) {
        this.randomTick = randomTick;
        return this;
    }

    public Props opacity(boolean variableOpacity) {
        this.variableOpacity = variableOpacity;
        return this;
    }

    public Props blocking(boolean blocksMovement) {
        this.blocksMovement = blocksMovement;
        return this;
    }

    public Props texture(String name, String texture) {
        if (textures == null) {
            textures = Textures.builder();
        }
        textures.add(name, Context.withNamespace(texture));
        return this;
    }

    public Props group(ItemGroup group) {
        this.group = group;
        return this;
    }

    private Block.Properties createBuilder() throws InitializationException {
        Block.Properties props;

        if (base != null) {
            props = Block.Properties.from(base.getBlock());
        } else if (color != null && material != null) {
            props = Block.Properties.create(material, color);
        } else if (material != null) {
            props = Block.Properties.create(material);
        } else {
            throw new InitializationException("Block.Builder requires a Material");
        }

        return props;
    }

    public Block.Properties toProperties() throws InitializationException {
        Block.Properties builder = createBuilder();
        set(sound, null, builder::sound);
        set(light, Integer.MAX_VALUE, builder::lightValue);
        set(slipperiness, Float.MAX_VALUE, builder::slipperiness);
        setBool(randomTick, false, builder::needsRandomTick);
        setBool(variableOpacity, false, builder::variableOpacity);
        setBool(blocksMovement, true, builder::doesNotBlockMovement);
        setPair(resistance, Float.MAX_VALUE, hardness, Float.MAX_VALUE, builder::hardnessAndResistance);
        return builder;
    }

    private static <V> void set(V value, V defValue, Consumer<V> consumer) {
        if (value != defValue) {
            consumer.accept(value);
        }
    }

    private static void setBool(boolean value, boolean defValue, Runnable runnable) {
        if (value != defValue) {
            runnable.run();
        }
    }

    private static <V1, V2> void setPair(V1 value1, V1 defValue1, V2 value2, V2 defValue2, BiConsumer<V1, V2> consumer) {
        if (value1 != defValue1 && value2 != defValue2) {
            consumer.accept(value1, value2);
        }
    }

    public static Props create() {
        return new Props();
    }

    public static Props create(IBlockState state) {
        return create().block(state);
    }

    public static Props create(Block block) {
        return create(block.getDefaultState());
    }
}
