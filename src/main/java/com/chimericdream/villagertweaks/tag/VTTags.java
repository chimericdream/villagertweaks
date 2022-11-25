package com.chimericdream.villagertweaks.tag;

import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class VTTags {
    public static final TagKey<Item> IGNORED_ITEMS = TagKey.of(Registry.ITEM_KEY, new Identifier("villagertweaks", "ignored_trade_items"));
    public static final TagKey<Item> TEMPTATION_ITEMS = TagKey.of(Registry.ITEM_KEY, new Identifier("villagertweaks", "villager_temptation_items"));
}
