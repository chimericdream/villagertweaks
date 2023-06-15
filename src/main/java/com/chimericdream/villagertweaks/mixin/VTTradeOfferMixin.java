package com.chimericdream.villagertweaks.mixin;

import com.chimericdream.villagertweaks.config.ConfigManager;
import com.chimericdream.villagertweaks.config.VillagerTweaksConfig;
import com.chimericdream.villagertweaks.tag.VTTags;
import net.minecraft.item.ItemStack;
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
	@Shadow private @Mutable int uses;
	@Shadow private int demandBonus;
	@Shadow private @Final ItemStack sellItem;

	@Inject(method = "<init>*", at = @At("RETURN"))
	public void forceHighMaxUseCount(CallbackInfo ci) {
		if (this.sellItem.isIn(VTTags.IGNORED_ITEMS)) {
			return;
		}

		VillagerTweaksConfig config = ConfigManager.getConfig();

		if (config.enableMaxTradeOverride) {
			if (config.maxTradesOverrideAmount > -1) {
				this.maxUses = config.maxTradesOverrideAmount;
			}
		}

		if (config.enableDemandBonusOverride) {
			this.demandBonus = 0;
		}
	}

	@Inject(method = "updateDemandBonus", at = @At("RETURN"))
	public void resetDemandBonus(CallbackInfo ci) {
		if (this.sellItem.isIn(VTTags.IGNORED_ITEMS)) {
			return;
		}

		VillagerTweaksConfig config = ConfigManager.getConfig();

		if (config.enableDemandBonusOverride) {
			this.demandBonus = 0;
		}
	}

	@Inject(method = "use", at = @At("TAIL"))
	public void checkInfiniteUses(CallbackInfo ci) {
		VillagerTweaksConfig config = ConfigManager.getConfig();

		if (config.enableMaxTradeOverride && config.maxTradesOverrideAmount == -1) {
			--this.uses;
		}
	}
}
