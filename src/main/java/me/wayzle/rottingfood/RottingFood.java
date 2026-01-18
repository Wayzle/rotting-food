package me.wayzle.rottingfood;

import me.wayzle.rottingfood.components.Components;
import me.wayzle.rottingfood.components.FoodStateComponent;
import net.fabricmc.api.ModInitializer;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;
import net.minecraft.item.consume.ConsumeEffect;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
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
    public static final Argument ARGUMENT_X = new Argument("x");
    public static final List<List<Expression>> LOADED_EXPRESSIONS = new ArrayList<>();
    public static final List<List<ConsumeEffect>> LOADED_EFFECTS = new ArrayList<>();

    public static FoodComponent modifyFoodComponent(FoodComponent originalFoodComponent, int foodStateIndex){
        ARGUMENT_X.setArgumentValue(originalFoodComponent.nutrition());
        int nurtition = (int)Math.max(Math.round(LOADED_EXPRESSIONS.get(foodStateIndex).get(0).calculate()), 0d);

        ARGUMENT_X.setArgumentValue(originalFoodComponent.saturation());
        float saturation = (float)Math.max(LOADED_EXPRESSIONS.get(foodStateIndex).get(1).calculate(), 0d);

        return new FoodComponent(nurtition, saturation, originalFoodComponent.canAlwaysEat());
    }

    public static ConsumableComponent modifyConsumableComponent(ConsumableComponent originalConsumableComponent, int foodStateIndex){
        ARGUMENT_X.setArgumentValue(originalConsumableComponent.consumeSeconds());
        float consumeSeconds = (float)Math.max(LOADED_EXPRESSIONS.get(foodStateIndex).get(2).calculate(), 0d);

        List<ConsumeEffect> stateEffects = new ArrayList<>(originalConsumableComponent.onConsumeEffects());
        stateEffects.addAll(LOADED_EFFECTS.get(foodStateIndex));

        return new ConsumableComponent(consumeSeconds, originalConsumableComponent.useAction(), originalConsumableComponent.sound(), originalConsumableComponent.hasConsumeParticles(), stateEffects);
    }

    public static void addFoodComponentsToStack(ItemStack stack, World world){
        stack.set(Components.FOOD_STATE_COMPONENT, new FoodStateComponent(world.getTime() / 24000, CONFIG.randomiseFoodState() ? (int)(Math.random() * Math.min(CONFIG.maxRandomState(), CONFIG.foodStates().size())) : 0));
        stack.set(DataComponentTypes.FOOD, modifyFoodComponent(stack.getItem().getComponents().get(DataComponentTypes.FOOD), stack.get(Components.FOOD_STATE_COMPONENT).state()));
        stack.set(DataComponentTypes.CONSUMABLE, modifyConsumableComponent(stack.getItem().getComponents().get(DataComponentTypes.CONSUMABLE), stack.get(Components.FOOD_STATE_COMPONENT).state()));
    }

    private void loadExpressions(){
        for(int i = 0; i < CONFIG.foodStates().size(); i++){
            LOADED_EXPRESSIONS.add(Arrays.asList(new Expression(CONFIG.foodStates().get(i).nurtition, ARGUMENT_X), new Expression(CONFIG.foodStates().get(i).saturation, ARGUMENT_X), new Expression(CONFIG.foodStates().get(i).eatSeconds, ARGUMENT_X)));
        }

    }

    private void loadEffects(){
        for(int i = 0; i < CONFIG.foodStates().size(); i++){
            LOADED_EFFECTS.add(new ArrayList<>());
            for(int j = 0; j < CONFIG.foodStates().get(i).effects.size(); j++){
                ConfigModel.EffectInstance instance = CONFIG.foodStates().get(i).effects.get(j);
                RegistryEntry<StatusEffect> effectRegistry = Registries.STATUS_EFFECT.getEntry(Registries.STATUS_EFFECT.get(Identifier.of(instance.id)));
                StatusEffectInstance statusEffectInstance = new StatusEffectInstance(effectRegistry, instance.duration, instance.amplifier, instance.ambient, instance.showParticles, instance.showIcon);
                ConsumeEffect consumeEffect = new ApplyEffectsConsumeEffect(statusEffectInstance, instance.probability);
                LOADED_EFFECTS.get(i).add(consumeEffect);
            }
        }
    }

    @Override
    public void onInitialize() {
        Components.init();

        License.iConfirmNonCommercialUse("Wayzle");
        mXparser.disableUlpRounding();
        mXparser.disableCanonicalRounding();
        mXparser.disableAlmostIntRounding();

        loadExpressions();
        loadEffects();
    }
}
