package me.wayzle.rottingfood;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Optional;

public class Components {
    public static final ComponentType<Long> FOOD_TIMESTAMP = Registry.register(
        Registries.DATA_COMPONENT_TYPE,
        Identifier.of(Rottingfood.MOD_ID, "food_timestamp"),
        ComponentType.<Long>builder()
                .codec(Codec.LONG)
                .build()
    );

    public static final ComponentType<Integer> FOOD_STATE = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(Rottingfood.MOD_ID, "food_state"),
            ComponentType.<Integer>builder()
                    .codec(Codec.INT)
                    .build()
    );

    protected static void init() {}
}
