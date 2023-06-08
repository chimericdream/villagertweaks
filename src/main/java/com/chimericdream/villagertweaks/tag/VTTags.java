package com.chimericdream.villagertweaks.tag;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class VTTags {
    public static final TagKey<Item> IGNORED_ITEMS = TagKey.of(Registries.ITEM.getKey(), new Identifier("villagertweaks", "ignored_trade_items"));
    public static final TagKey<Item> TEMPTATION_ITEMS = TagKey.of(Registries.ITEM.getKey(), new Identifier("villagertweaks", "villager_temptation_items"));
}
