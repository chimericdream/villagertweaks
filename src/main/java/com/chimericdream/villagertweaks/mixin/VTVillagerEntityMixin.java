package com.chimericdream.villagertweaks.mixin;

import com.chimericdream.villagertweaks.VillagerTweaksMod;
import com.chimericdream.villagertweaks.config.ConfigManager;
import com.chimericdream.villagertweaks.config.VillagerTweaksConfig;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityInteraction;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.village.VillageGossipType;
import net.minecraft.village.VillagerGossips;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(VillagerEntity.class)
public abstract class VTVillagerEntityMixin extends MerchantEntity {
    private final UUID GLOBAL_UUID = UUID.fromString("00000001-0000-0001-0000-000100000001");

    @Shadow private @Final VillagerGossips gossip;

    public VTVillagerEntityMixin(EntityType<? extends MerchantEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "getReputation", at = @At("HEAD"), cancellable = true)
    private void injected(PlayerEntity player, CallbackInfoReturnable<Integer> cir) {
        VillagerTweaksConfig config = ConfigManager.getConfig();
        UUID playerId = config.enableGlobalReputation ? GLOBAL_UUID : player.getUuid();

        if (config.enableBadReputation) {
            return;
        }

        cir.setReturnValue(this.gossip.getReputationFor(playerId, (t) -> t != VillageGossipType.MINOR_NEGATIVE && t != VillageGossipType.MAJOR_NEGATIVE));
    }

    @Inject(method = "interactMob",at = @At("HEAD"), cancellable = true)
    private void vt_bagTheVillager(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (!this.getWorld().isClient) {
            ItemStack itemStack = player.getStackInHand(hand);

            if (itemStack.getItem() == Items.BUNDLE && player.isSneaking()) {
                VillagerTweaksConfig config = ConfigManager.getConfig();

                this.gossip.startGossip(
                    config.enableGlobalReputation ? GLOBAL_UUID : player.getUuid(),
                    VillageGossipType.MINOR_NEGATIVE,
                    25
                );

                ItemStack newItemStack = new ItemStack(VillagerTweaksMod.BAGGED_VILLAGER_ITEM);
                NbtCompound nbt = new NbtCompound();
                this.writeCustomDataToNbt(nbt);
                NbtComponent nbtComponent = NbtComponent.of(nbt);

                newItemStack.set(DataComponentTypes.CUSTOM_DATA, nbtComponent);

                player.giveItemStack(newItemStack);
                itemStack.decrement(1);
                this.discard();
                cir.setReturnValue(ActionResult.SUCCESS);
            }
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
