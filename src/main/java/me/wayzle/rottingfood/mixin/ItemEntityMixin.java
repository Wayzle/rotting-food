package me.wayzle.rottingfood.mixin;

import me.wayzle.rottingfood.Components;
import me.wayzle.rottingfood.ConfigModel;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.wayzle.rottingfood.Rottingfood.CONFIG;
import static me.wayzle.rottingfood.Rottingfood.modifyFoodComponent;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {
    @Inject(
            method = "<init>(Lnet/minecraft/world/World;DDDLnet/minecraft/item/ItemStack;)V",
            at = @At("TAIL")
    )
    public void rottenfood$componentInit(World world, double x, double y, double z, ItemStack stack, CallbackInfo ci){
        FoodComponent foodComponent = (FoodComponent) stack.getItem().getComponents().get(DataComponentTypes.FOOD);
        if (foodComponent != null) {
            if(CONFIG.modDataComponentsEnabled() && !CONFIG.exclude().contains(stack.getRegistryEntry().getIdAsString())){
                long currentDay = world.getTime() / 24000;
                if(!stack.getComponents().contains(Components.FOOD_TIMESTAMP) && !stack.getComponents().contains(Components.FOOD_STATE)){
                    stack.set(Components.FOOD_TIMESTAMP, currentDay);
                    stack.set(Components.FOOD_STATE, 0);
                    stack.set(DataComponentTypes.FOOD, modifyFoodComponent(stack.getItem().getComponents().get(DataComponentTypes.FOOD), CONFIG.foodStates().get(0)));
                }
            }

        }
    }

    @Inject(
            method = "<init>(Lnet/minecraft/world/World;DDDLnet/minecraft/item/ItemStack;DDD)V",
            at = @At("TAIL")
    )
    public void rottenfood$componentInit(World world, double x, double y, double z, ItemStack stack, double velocityX, double velocityY, double velocityZ, CallbackInfo ci){
        FoodComponent foodComponent = (FoodComponent) stack.getItem().getComponents().get(DataComponentTypes.FOOD);
        if (foodComponent != null) {
            if(CONFIG.modDataComponentsEnabled() && !CONFIG.exclude().contains(stack.getRegistryEntry().getIdAsString())){
                long currentDay = world.getTime() / 24000;
                if(!stack.getComponents().contains(Components.FOOD_TIMESTAMP) && !stack.getComponents().contains(Components.FOOD_STATE)){
                    stack.set(Components.FOOD_TIMESTAMP, currentDay);
                    stack.set(Components.FOOD_STATE, 0);
                    stack.set(DataComponentTypes.FOOD, modifyFoodComponent(stack.getItem().getComponents().get(DataComponentTypes.FOOD), CONFIG.foodStates().get(0)));
                }
            }

        }
    }

    @Inject(
            method = "<init>(Lnet/minecraft/entity/ItemEntity;)V",
            at = @At("TAIL")
    )
    private void rottenfood$componentInit(ItemEntity entity, CallbackInfo ci){
        ItemStack stack = entity.getStack();
        FoodComponent foodComponent = (FoodComponent) stack.getItem().getComponents().get(DataComponentTypes.FOOD);
        if (foodComponent != null) {
            if(CONFIG.modDataComponentsEnabled() && !CONFIG.exclude().contains(stack.getRegistryEntry().getIdAsString())){
                long currentDay = entity.getWorld().getTime() / 24000;
                if(!stack.getComponents().contains(Components.FOOD_TIMESTAMP) && !stack.getComponents().contains(Components.FOOD_STATE)){
                    stack.set(Components.FOOD_TIMESTAMP, currentDay);
                    stack.set(Components.FOOD_STATE, 0);
                    stack.set(DataComponentTypes.FOOD, modifyFoodComponent(stack.getItem().getComponents().get(DataComponentTypes.FOOD), CONFIG.foodStates().get(0)));
                }
            }

        }
    }

//    @Inject(
//            method = "<init>(Lnet/minecraft/entity/EntityType;Lnet/minecraft/world/World;)V",
//            at = @At("TAIL")
//    )
//    public static void rottenfood$componentInit(EntityType entityType, World world, CallbackInfo ci){
//        ItemStack stack = entityType.getI.getStack();
//        FoodComponent foodComponent = (FoodComponent) stack.getItem().getComponents().get(DataComponentTypes.FOOD);
//        if (foodComponent != null) {
//            if(CONFIG.modDataComponentsEnabled() && !CONFIG.exclude().contains(stack.getRegistryEntry().getIdAsString())){
//                long currentDay = world.getTime() / 24000;
//                if(!stack.getComponents().contains(Components.FOOD_TIMESTAMP) && !stack.getComponents().contains(Components.FOOD_STATE)){
//                    stack.set(Components.FOOD_TIMESTAMP, currentDay);
//                    stack.set(Components.FOOD_STATE, 0);
//                    stack.set(DataComponentTypes.FOOD, modifyFoodComponent(stack.getItem().getComponents().get(DataComponentTypes.FOOD), CONFIG.foodStates().get(0)));
//                }
//            }
//
//        }
//    }
}
