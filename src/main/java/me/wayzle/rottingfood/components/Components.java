package me.wayzle.rottingfood.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.wayzle.rottingfood.RottingFood;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class Components {
    public static final ComponentType<FoodStateComponent> FOOD_STATE_COMPONENT = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(RottingFood.MOD_ID, "food_state"),
            ComponentType.<FoodStateComponent>builder().codec(FoodStateComponent.CODEC).build()
    );

    public static void init() {}
}
