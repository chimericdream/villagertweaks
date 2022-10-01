package com.chimericdream.villagertweaks.mixin;

import com.chimericdream.villagertweaks.config.ConfigManager;
import com.chimericdream.villagertweaks.config.VillagerTweaksConfig;
import net.minecraft.entity.mob.ZombieVillagerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ZombieVillagerEntity.class)
public class VTZombieVillagerEntityMixin {
    @ModifyArg(
        method = "interactMob",
        at = @At(value = "INVOKE", target = "net/minecraft/entity/mob/ZombieVillagerEntity.setConverting(Ljava/util/UUID;I)V"),
        index = 1
    )
    private int modifyConversionTime(int time) {
        VillagerTweaksConfig config = ConfigManager.getConfig();

        if (config.enableConversionTimeOverride) {
            return config.conversionTime;
        }

        return time;
    }
}
