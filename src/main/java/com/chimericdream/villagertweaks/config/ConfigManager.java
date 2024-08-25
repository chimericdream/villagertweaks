package com.chimericdream.villagertweaks.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;

import java.util.Objects;
import java.util.function.Consumer;

public class ConfigManager {
    private static ConfigHolder<VillagerTweaksConfig> holder;

    public static final Consumer<VillagerTweaksConfig> DEFAULT = (config) -> {
        config.reset = "Erase to reset";
        config.enableMaxTradeOverride = false;
        config.maxTradesOverrideAmount = -1;
        config.enableConversionTimeOverride = false;
        config.conversionTime = 3600;
        config.enableDemandBonusOverride = false;
        config.enableEmeraldTemptation = false;
        config.enableGlobalReputation = false;
        config.enableBadReputation = true;
        config.forceVillagerConversion = true;
        config.displayConversionTime = false;
    };

    public static void registerAutoConfig() {
        if (holder != null) {
            throw new IllegalStateException("Configuration already registered");
        }

        holder = AutoConfig.register(VillagerTweaksConfig.class, JanksonConfigSerializer::new);

        if (holder.getConfig().reset == null || Objects.equals(holder.getConfig().reset, "")) {
            DEFAULT.accept(holder.getConfig());
        }

        holder.save();
    }

    public static VillagerTweaksConfig getConfig() {
        if (holder == null) {
            return new VillagerTweaksConfig();
        }

        return holder.getConfig();
    }

    public static void load() {
        if (holder == null) {
            registerAutoConfig();
        }

        holder.load();
    }

    public static void save() {
        if (holder == null) {
            registerAutoConfig();
        }

        holder.save();
    }
}
