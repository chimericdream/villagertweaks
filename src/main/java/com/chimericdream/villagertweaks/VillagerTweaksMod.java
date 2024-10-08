package com.chimericdream.villagertweaks;

import com.chimericdream.villagertweaks.config.ConfigManager;
import com.chimericdream.villagertweaks.item.BaggedVillagerItem;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VillagerTweaksMod implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("villagertweaks");

    public static final BaggedVillagerItem BAGGED_VILLAGER_ITEM = new BaggedVillagerItem(new Item.Settings().maxCount(1));

    static {
        ConfigManager.registerAutoConfig();
    }

    @Override
    public void onInitialize() {
        Registry.register(Registries.ITEM, Identifier.of("villagertweaks", "bagged_villager"), BAGGED_VILLAGER_ITEM);
    }
}
