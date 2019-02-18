package com.conquestreforged.core.block.props;

import com.conquestreforged.core.block.annotation.Name;
import net.minecraft.util.ResourceLocation;

import java.lang.annotation.Annotation;

public class BlockName {

    private final String namespace;
    private final String plural;
    private final String singular;

    private BlockName(String namespace, String plural, String singular) {
        this.namespace = namespace;
        this.plural = plural;
        this.singular = singular;
    }

    public String getNamespace() {
        return namespace;
    }

    public String format(String format, boolean plural) {
        return String.format(format, plural ? this.plural : this.singular);
    }

    public ResourceLocation format(Name name) {
        return new ResourceLocation(namespace, format(name.value(), name.plural()));
    }

    public static BlockName of(String namespace, String plural, String singular) {
        return new BlockName(namespace, plural, singular);
    }

    public static Name getNameFormat(Class<?> type) {
        Name name = type.getAnnotation(Name.class);
        return name == null ? BlockName.DEFAULT : name;
    }

    private static Name DEFAULT = new Name() {

        @Override
        public Class<? extends Annotation> annotationType() {
            return Name.class;
        }

        @Override
        public String value() {
            return "%s";
        }

        @Override
        public boolean plural() {
            return true;
        }
    };
}
