package com.chimericdream.villagertweaks.mixin;

import com.chimericdream.villagertweaks.config.ConfigManager;
import com.chimericdream.villagertweaks.config.VillagerTweaksConfig;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.Difficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ZombieEntity.class)
public class VTZombieEntityMixin {
    @Redirect(
        method = "onKilledOther",
        at = @At(value = "INVOKE", target = "net/minecraft/server/world/ServerWorld.getDifficulty()Lnet/minecraft/world/Difficulty;")
    )
    private Difficulty modifyConversionTime(ServerWorld world) {
        VillagerTweaksConfig config = ConfigManager.getConfig();

        if (config.forceVillagerConversion) {
            return Difficulty.HARD;
        }

        return world.getDifficulty();
    }
}
