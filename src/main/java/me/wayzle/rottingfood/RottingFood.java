package me.wayzle.rottingfood;

import me.wayzle.rottingfood.components.Components;
import me.wayzle.rottingfood.components.FoodStateComponent;
import net.fabricmc.api.ModInitializer;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.License;
import org.mariuszgromada.math.mxparser.mXparser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import me.wayzle.rottingfood.RottingFoodConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RottingFood implements ModInitializer {

    public static final String MOD_ID = "rottingfood";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final RottingFoodConfig CONFIG = RottingFoodConfig.createAndLoad();
    public static final Argument argumentX = new Argument("x");
    public static final List<List<Expression>> expressions = new ArrayList<>();

    public static FoodComponent modifyFoodComponent(FoodComponent originalFoodComponent, int foodStateIndex){
        argumentX.setArgumentValue(originalFoodComponent.nutrition());
        int nurtition = (int)Math.max(Math.round(expressions.get(foodStateIndex).get(0).calculate()), 0d);

        argumentX.setArgumentValue(originalFoodComponent.saturation());
        float saturation = (float)Math.max(expressions.get(foodStateIndex).get(1).calculate(), 0d);

        argumentX.setArgumentValue(originalFoodComponent.eatSeconds());
        float eatSeconds = (float)Math.max(expressions.get(foodStateIndex).get(2).calculate(), 0d);

        return new FoodComponent(nurtition, saturation, originalFoodComponent.canAlwaysEat(), eatSeconds, originalFoodComponent.usingConvertsTo(), originalFoodComponent.effects());
    }

    public static void addFoodComponentsToStack(ItemStack stack, World world){
        stack.set(Components.FOOD_STATE_COMPONENT, new FoodStateComponent(world.getTime() / 24000, CONFIG.randomiseFoodState() ? (int)(Math.random() * Math.min(CONFIG.maxRandomState(), CONFIG.foodStates().size())) : 0));
        stack.set(DataComponentTypes.FOOD, modifyFoodComponent(stack.getItem().getComponents().get(DataComponentTypes.FOOD), stack.get(Components.FOOD_STATE_COMPONENT).state()));
    }

    private void initExpressions(){
        for(int i = 0; i < CONFIG.foodStates().size(); i++){
            expressions.add(Arrays.asList(new Expression(CONFIG.foodStates().get(i).nurtition, argumentX), new Expression(CONFIG.foodStates().get(i).saturation, argumentX), new Expression(CONFIG.foodStates().get(i).eatSeconds, argumentX)));
        }

    }

    @Override
    public void onInitialize() {
        Components.init();

        License.iConfirmNonCommercialUse("Wayzle");
        mXparser.disableUlpRounding();
        mXparser.disableCanonicalRounding();
        mXparser.disableAlmostIntRounding();

        initExpressions();
    }
}
