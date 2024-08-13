package com.chimericdream.villagertweaks.config;

import com.chimericdream.villagertweaks.VillagerTweaksMod;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "villagertweaks")
@Config.Gui.Background("minecraft:textures/block/composter_side.png")
public class VillagerTweaksConfig implements ConfigData {
    public String reset = "";
    public boolean enableMaxTradeOverride = false;
    public int maxTradesOverrideAmount = -1;
    public boolean enableConversionTimeOverride = false;
    public int conversionTime = 3600;
    public boolean enableDemandBonusOverride = false;
    public boolean enableEmeraldTemptation = false;
    public boolean enableGlobalReputation = false;
    public boolean enableBadReputation = true;
    public boolean forceVillagerConversion = false;

    public void validatePostLoad() {
        if (this.conversionTime < 1) {
            VillagerTweaksMod.LOGGER.info("[config] Invalid conversion time found! Disabling conversion time override and resetting time to default.");

            this.enableConversionTimeOverride = false;
            this.conversionTime = 3600;
        }

        if (this.maxTradesOverrideAmount == 0) {
            VillagerTweaksMod.LOGGER.info("[config] Max trades cannot be zero! Disabling max trade override and resetting count to default.");

            this.enableMaxTradeOverride = false;
            this.maxTradesOverrideAmount = -1;
        }
    }
}
