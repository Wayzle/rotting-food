package me.wayzle.rottingfood.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record FoodStateComponent(long timestamp, int state) {
    public static final Codec<FoodStateComponent> CODEC = RecordCodecBuilder.create(builder -> {
        return builder.group(
                Codec.LONG.fieldOf("timestamp").forGetter(FoodStateComponent::timestamp),
                Codec.INT.fieldOf("state").forGetter(FoodStateComponent::state)
        ).apply(builder, FoodStateComponent::new);
    });
}
