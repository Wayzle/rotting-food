package me.wayzle.rottingfood.mixin;

import me.wayzle.rottingfood.components.Components;
import me.wayzle.rottingfood.ConfigModel;
import me.wayzle.rottingfood.components.FoodStateComponent;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

import static me.wayzle.rottingfood.RottingFood.*;

@Mixin(Item.class)
public abstract class ItemMixin {

    @Shadow
    public abstract ComponentMap getComponents();

    @Inject(
            method = "inventoryTick",
            at = @At("HEAD")
    )
    public void rottenfood$rottingFoodTick(ItemStack stack, World world, Entity entity, int slot, boolean selected, CallbackInfo ci) {
        if (!world.isClient()) {
            FoodComponent foodComponent = (FoodComponent) this.getComponents().get(DataComponentTypes.FOOD);
            if (foodComponent != null && !CONFIG.exclude().contains(stack.getRegistryEntry().getIdAsString())) {
                long currentDay = world.getTime() / 24000;
                FoodStateComponent foodStateComponent = stack.getComponents().get(Components.FOOD_STATE_COMPONENT);

                if(foodStateComponent != null){
                    if(CONFIG.modDataComponentsEnabled()){
                        int currentStateID = foodStateComponent.state();
                        long difference = currentDay - foodStateComponent.timestamp();
                        ConfigModel.FoodState currentState = CONFIG.foodStates().get(currentStateID);

                        if(CONFIG.foodStates().size() > currentStateID + 1 && difference >= currentState.duration){
                            stack.set(Components.FOOD_STATE_COMPONENT, new FoodStateComponent(foodStateComponent.timestamp() + currentState.duration, currentStateID + 1));
                            stack.set(DataComponentTypes.FOOD, modifyFoodComponent(foodComponent, currentStateID + 1));
                            stack.set(DataComponentTypes.CONSUMABLE, modifyConsumableComponent(this.getComponents().get(DataComponentTypes.CONSUMABLE), currentStateID + 1));
                        }
                    }
                    else {
                        stack.remove(Components.FOOD_STATE_COMPONENT);
                        stack.set(DataComponentTypes.FOOD, this.getComponents().get(DataComponentTypes.FOOD));
                    }
                }
                else {
                    if(CONFIG.modDataComponentsEnabled()){
                        addFoodComponentsToStack(stack, world);
                    }
                }
            }
        }
    }

    @Inject(method = "appendTooltip", at = @At("TAIL"))
    private void rottenfood$afterAppendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type, CallbackInfo ci) {
        if(CONFIG.showInTooltip() && CONFIG.modDataComponentsEnabled()){
            if (stack.getComponents().contains(Components.FOOD_STATE_COMPONENT)) {
                ConfigModel.FoodState currentState = CONFIG.foodStates().get(stack.get(Components.FOOD_STATE_COMPONENT).state());

                tooltip.add(Text.literal(currentState.tooltip).setStyle(Style.EMPTY.withColor(currentState.color)));
            }
        }
    }

}
