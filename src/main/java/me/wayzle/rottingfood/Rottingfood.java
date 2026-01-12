package me.wayzle.rottingfood;

import net.fabricmc.api.ModInitializer;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.potion.Potion;
import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.License;
import org.mariuszgromada.math.mxparser.mXparser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import me.wayzle.rottingfood.RottingFoodConfig;

import java.util.ArrayList;

public class Rottingfood implements ModInitializer {

    public static final String MOD_ID = "rottingfood";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final RottingFoodConfig CONFIG = RottingFoodConfig.createAndLoad();

    public static FoodComponent modifyFoodComponent(FoodComponent originalFoodComponent, ConfigModel.FoodState foodState){
        Argument x = new Argument("x");
        Expression enutrition = new Expression(foodState.nurtition, x);
        Expression esaturation = new Expression(foodState.saturation, x);
        Expression eeatSeconds = new Expression(foodState.eatSeconds, x);

        x.setArgumentValue(originalFoodComponent.nutrition());
        int nurtition = (int)Math.max(Math.round(enutrition.calculate()), 0d);

        x.setArgumentValue(originalFoodComponent.saturation());
        float saturation = (float)Math.max(esaturation.calculate(), 0d);

        x.setArgumentValue(originalFoodComponent.eatSeconds());
        float eatSeconds = (float)Math.max(eeatSeconds.calculate(), 0d);

        return new FoodComponent(nurtition, saturation, originalFoodComponent.canAlwaysEat(), eatSeconds, originalFoodComponent.usingConvertsTo(), originalFoodComponent.effects());
    }

    @Override
    public void onInitialize() {
        Components.init();

        License.iConfirmNonCommercialUse("Wayzle");
        mXparser.disableUlpRounding();
        mXparser.disableCanonicalRounding();
        mXparser.disableAlmostIntRounding();
    }
}
