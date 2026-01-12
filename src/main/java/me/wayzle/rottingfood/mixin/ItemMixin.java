package me.wayzle.rottingfood.mixin;

import me.wayzle.rottingfood.Components;
import me.wayzle.rottingfood.ConfigModel;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.mXparser;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

import static com.mojang.text2speech.Narrator.LOGGER;
import static me.wayzle.rottingfood.Rottingfood.CONFIG;
import static me.wayzle.rottingfood.Rottingfood.modifyFoodComponent;

@Mixin(Item.class)
public abstract class ItemMixin {

    @Shadow
    public abstract ComponentMap getComponents();

    @Inject(
            method = "inventoryTick",
            at = @At("HEAD")
    )
    public void rottenfood$rottingFoodTick(ItemStack stack, World world, Entity entity, int slot, boolean selected, CallbackInfo ci) {
        FoodComponent foodComponent = (FoodComponent) this.getComponents().get(DataComponentTypes.FOOD);
        if (foodComponent != null) {
            if(CONFIG.modDataComponentsEnabled() && !CONFIG.exclude().contains(stack.getRegistryEntry().getIdAsString())){
                long currentDay = world.getTime() / 24000;
                if(!stack.getComponents().contains(Components.FOOD_TIMESTAMP) && !stack.getComponents().contains(Components.FOOD_STATE)){
                    stack.set(Components.FOOD_TIMESTAMP, currentDay);
                    stack.set(Components.FOOD_STATE, 0);
                    stack.set(DataComponentTypes.FOOD, modifyFoodComponent(this.getComponents().get(DataComponentTypes.FOOD), CONFIG.foodStates().get(0)));
                }
                else{
                    long difference = currentDay - stack.get(Components.FOOD_TIMESTAMP);
                    int currentStateID = stack.get(Components.FOOD_STATE);
                    ConfigModel.FoodState currentState = CONFIG.foodStates().get(currentStateID);

                    if(CONFIG.foodStates().size() > currentStateID + 1 && difference >= currentState.duration){
                        stack.set(Components.FOOD_TIMESTAMP, stack.get(Components.FOOD_TIMESTAMP) + currentState.duration);
                        stack.set(Components.FOOD_STATE, currentStateID + 1);
                        stack.set(DataComponentTypes.FOOD, modifyFoodComponent(this.getComponents().get(DataComponentTypes.FOOD), CONFIG.foodStates().get(currentStateID + 1)));
                    }
                }
            }
            else{
                if(stack.getComponents().contains(Components.FOOD_TIMESTAMP)){
                    stack.remove(Components.FOOD_TIMESTAMP);
                    stack.remove(Components.FOOD_STATE);
                    stack.set(DataComponentTypes.FOOD, this.getComponents().get(DataComponentTypes.FOOD));
                }
            }

        }
    }

    @Inject(method = "appendTooltip", at = @At("TAIL"))
    private void rottenfood$afterAppendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type, CallbackInfo ci) {
        if(CONFIG.showInTooltip() && CONFIG.modDataComponentsEnabled()){
            FoodComponent foodComponent = (FoodComponent) this.getComponents().get(DataComponentTypes.FOOD);
            if (foodComponent != null && stack.getComponents().contains(Components.FOOD_TIMESTAMP) && stack.getComponents().contains(Components.FOOD_STATE)) {
                ConfigModel.FoodState currentState = CONFIG.foodStates().get(stack.get(Components.FOOD_STATE));

                tooltip.add(Text.literal(currentState.tooltip).setStyle(Style.EMPTY.withColor(currentState.color)));
            }
        }
    }

}
