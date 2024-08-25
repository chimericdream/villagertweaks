package com.chimericdream.villagertweaks.mixin;

import com.chimericdream.villagertweaks.config.ConfigManager;
import com.chimericdream.villagertweaks.config.VillagerTweaksConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ZombieVillagerEntity.class)
public abstract class VTZombieVillagerEntityMixin extends Entity {
    @Shadow private int conversionTimer;

    @Shadow public abstract boolean isConverting();

    public VTZombieVillagerEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Unique
    private Text prevName = null;

    @Unique
    private boolean wasPrevNameVisible = false;

    @Unique
    private boolean isTimerShowing = false;

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

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        VillagerTweaksConfig config = ConfigManager.getConfig();

        if (!this.isConverting() || !config.displayConversionTime || this.conversionTimer <= 0) {
            return;
        }

        int secondsLeft = this.conversionTimer / 20;
        if (isTimerShowing) {
            this.setCustomName(getFormattedTime(secondsLeft));
        } else {
            if (this.hasCustomName()) {
                this.prevName = this.getCustomName();
                this.wasPrevNameVisible = this.isCustomNameVisible();
            }

            this.setCustomName(getFormattedTime(secondsLeft));

            this.setCustomNameVisible(true);
            this.isTimerShowing = true;
        }
    }

    @Inject(method = "finishConversion", at = @At("HEAD"))
    private void onFinishConversion(CallbackInfo ci) {
        if (this.prevName != null) {
            this.setCustomName(this.prevName);
            this.setCustomNameVisible(this.wasPrevNameVisible);
        } else {
            this.setCustomName(null);
        }

        this.isTimerShowing = false;
    }

    @Unique
    private Text getFormattedTime(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds - (minutes * 60);

        return Text.of(minutes + ":" + (seconds < 10 ? "0" + seconds : seconds));
    }
}
