package me.wayzle.rottingfood;

import io.wispforest.owo.config.annotation.*;

import java.util.*;

import static me.wayzle.rottingfood.RottingFood.MOD_ID;

@Modmenu(modId = MOD_ID)
@Config(name = "rotting-food-config", wrapperName = "RottingFoodConfig")
public class ConfigModel {

    public static class EffectInstance {
        public float probability;
        public String id;
        public int duration = 0;
        public int amplifier = 0;
        public boolean ambient;
        public boolean showParticles;
        public boolean showIcon;

        public EffectInstance() {}

        public EffectInstance(float probability, String id, int duration, int amplifier, boolean ambient, boolean showParticles, boolean showIcon) {
            this.probability = probability;
            this.id = id;
            this.duration = duration;
            this.amplifier = amplifier;
            this.ambient = ambient;
            this.showParticles = showParticles;
            this.showIcon = showIcon;
        }
    }

    public static class FoodState {

        public String tooltip = "";
        public int color = 0;
        public int duration = 0;
        public String nurtition = "x";
        public String saturation = "x";
        public String eatSeconds = "x";
        public List<EffectInstance> effects = new ArrayList<>();

        public FoodState() {}

        public FoodState(String tooltip, int color, int duration, String nurtition, String saturation, String eatSeconds, List<EffectInstance> effects) {
            this.tooltip = tooltip;
            this.color = color;
            this.duration = duration;
            this.nurtition = nurtition;
            this.saturation = saturation;
            this.eatSeconds = eatSeconds;
            this.effects = effects;
        }
    }

    public boolean modDataComponentsEnabled = true;
    public boolean showInTooltip = true;
    public boolean randomiseFoodState = false;
    public boolean preventFoodFarms = true;

    @RangeConstraint(min = 1, max=20)
    public int maxRandomState = 2;

    @SectionHeader("notice")
    public List<String> exclude = new ArrayList<String>();
    public List<FoodState> foodStates = new ArrayList<>();


    public ConfigModel() {
        exclude.add("minecraft:golden_apple");
        exclude.add("minecraft:enchanted_golden_apple");
        exclude.add("minecraft:golden_carrot");

        foodStates.add(new FoodState("Fresh", 5635925, 1, "x", "x", "x", new ArrayList<>()));
        foodStates.add(new FoodState("Edible", 16777045, 3, "x/2", "x/2", "x", new ArrayList<>()));
        foodStates.add(new FoodState("Rotten", 16733525, -1, "0", "0", "x*2", new ArrayList<>(Arrays.asList(new EffectInstance(1f, "minecraft:nausea", 200, 1, true, false, false)))));
    }
}
