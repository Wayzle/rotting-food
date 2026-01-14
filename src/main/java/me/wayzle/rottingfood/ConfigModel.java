package me.wayzle.rottingfood;

import io.wispforest.owo.config.annotation.*;

import java.util.*;

import static me.wayzle.rottingfood.RottingFood.MOD_ID;

@Modmenu(modId = MOD_ID)
@Config(name = "rotting-food-config", wrapperName = "RottingFoodConfig")
public class ConfigModel {

    public static class FoodState {

        public String tooltip = "";
        public int color = 0;
        public int duration = 0;
        public String nurtition = "x";
        public String saturation = "x";
        public String eatSeconds = "x";

        public FoodState() {}

        public FoodState(String tooltip, int color, int duration, String nurtition, String saturation, String eatSeconds) {
            this.tooltip = tooltip;
            this.color = color;
            this.duration = duration;
            this.nurtition = nurtition;
            this.saturation = saturation;
            this.eatSeconds = eatSeconds;
        }
    }

    public boolean modDataComponentsEnabled = true;
    public boolean showInTooltip = true;
    public boolean randomiseFoodState = false;

    @RangeConstraint(min = 1, max=20)
    public int maxRandomState = 2;

    @SectionHeader("notice")
    public List<String> exclude = new ArrayList<String>();
    public List<FoodState> foodStates = new ArrayList<>();


    public ConfigModel() {
        exclude.add("minecraft:golden_apple");
        exclude.add("minecraft:enchanted_golden_apple");
        exclude.add("minecraft:golden_carrot");

        foodStates.add(new FoodState("Fresh", 5635925, 1, "x", "x", "x"));
        foodStates.add(new FoodState("Edible", 16777045, 3, "x/2", "x/2", "x"));
        foodStates.add(new FoodState("Rotten", 16733525, -1, "0", "0", "x*2"));
    }
}
