package com.chimericdream.villagertweaks.mixin;

import com.chimericdream.villagertweaks.config.ConfigManager;
import com.chimericdream.villagertweaks.config.VillagerTweaksConfig;
import com.chimericdream.villagertweaks.goals.VTEmeraldTemptationGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.village.VillagerType;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VillagerEntity.class)
abstract public class VTEmeraldTemptationMixin extends MerchantEntity {
    public VTEmeraldTemptationMixin(EntityType<? extends MerchantEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "<init>(Lnet/minecraft/entity/EntityType;Lnet/minecraft/world/World;Lnet/minecraft/village/VillagerType;)V", at = @At(value = "TAIL"))
    public void addEmeraldTemptationGoal(EntityType<? extends VillagerEntity> entityType, World world, VillagerType type, CallbackInfo ci) {
        VillagerTweaksConfig config = ConfigManager.getConfig();

        if (config.enableEmeraldTemptation) {
            this.goalSelector.add(2, new VTEmeraldTemptationGoal(this));
        }
    }
}
