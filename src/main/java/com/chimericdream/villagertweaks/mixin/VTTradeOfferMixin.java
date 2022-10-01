package com.chimericdream.villagertweaks.mixin;

import com.chimericdream.villagertweaks.config.ConfigManager;
import com.chimericdream.villagertweaks.config.VillagerTweaksConfig;
import net.minecraft.village.TradeOffer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TradeOffer.class)
public class VTTradeOfferMixin {
	@Shadow private @Final @Mutable int maxUses;
	@Shadow private int demandBonus;

	@Inject(method = "<init>*", at = @At("RETURN"))
	public void forceHighMaxUseCount(CallbackInfo ci) {
		VillagerTweaksConfig config = ConfigManager.getConfig();

		if (config.enableMaxTradeOverride) {
			if (config.maxTradesOverrideAmount > -1) {
				this.maxUses = config.maxTradesOverrideAmount;
			} else {
				this.maxUses = 999999;
			}
		}

		if (config.enableDemandBonusOverride) {
			this.demandBonus = 0;
		}
	}

	@Inject(method = "updateDemandBonus", at = @At("RETURN"))
	public void resetDemandBonus(CallbackInfo ci) {
		VillagerTweaksConfig config = ConfigManager.getConfig();

		if (config.enableDemandBonusOverride) {
			this.demandBonus = 0;
		}
	}
}