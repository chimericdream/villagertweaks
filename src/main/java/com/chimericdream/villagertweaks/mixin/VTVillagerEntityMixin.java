package com.chimericdream.villagertweaks.mixin;

import com.chimericdream.villagertweaks.config.ConfigManager;
import com.chimericdream.villagertweaks.config.VillagerTweaksConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityInteraction;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.village.VillageGossipType;
import net.minecraft.village.VillagerGossips;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(VillagerEntity.class)
public abstract class VTVillagerEntityMixin {
    private final UUID GLOBAL_UUID = UUID.fromString("00000001-0000-0001-0000-000100000001");

    @Shadow private @Final VillagerGossips gossip;

    @Inject(method = "getReputation", at = @At("HEAD"), cancellable = true)
    private void injected(CallbackInfoReturnable<Integer> cir) {
        VillagerTweaksConfig config = ConfigManager.getConfig();
        if (!config.enableGlobalReputation) {
            return;
        }

        if (config.enableBadReputation) {
            cir.setReturnValue(this.gossip.getReputationFor(GLOBAL_UUID, (t) -> true));
        } else {
            cir.setReturnValue(this.gossip.getReputationFor(GLOBAL_UUID, (t) -> t != VillageGossipType.MINOR_NEGATIVE && t != VillageGossipType.MAJOR_NEGATIVE));
        }
    }

    @Inject(method = "onInteractionWith", at = @At(value = "HEAD"), cancellable = true)
    private void vt_overrideSettingGossip(EntityInteraction interaction, Entity entity, CallbackInfo ci) {
        VillagerTweaksConfig config = ConfigManager.getConfig();
        if (!config.enableGlobalReputation) {
            return;
        }

        if (entity instanceof PlayerEntity) {
            if (interaction == EntityInteraction.ZOMBIE_VILLAGER_CURED) {
                this.gossip.startGossip(GLOBAL_UUID, VillageGossipType.MAJOR_POSITIVE, 20);
                this.gossip.startGossip(GLOBAL_UUID, VillageGossipType.MINOR_POSITIVE, 25);
            } else if (interaction == EntityInteraction.TRADE) {
                this.gossip.startGossip(GLOBAL_UUID, VillageGossipType.TRADING, 2);
            } else if (interaction == EntityInteraction.VILLAGER_HURT) {
                this.gossip.startGossip(GLOBAL_UUID, VillageGossipType.MINOR_NEGATIVE, 25);
            } else if (interaction == EntityInteraction.VILLAGER_KILLED) {
                this.gossip.startGossip(GLOBAL_UUID, VillageGossipType.MAJOR_NEGATIVE, 25);
            }

            ci.cancel();
        }
    }
}
