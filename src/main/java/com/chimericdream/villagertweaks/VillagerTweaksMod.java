package com.chimericdream.villagertweaks;

import com.chimericdream.villagertweaks.config.ConfigManager;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VillagerTweaksMod implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("villagertweaks");

	static {
		ConfigManager.registerAutoConfig();
	}

	@Override
	public void onInitialize() {}
}
