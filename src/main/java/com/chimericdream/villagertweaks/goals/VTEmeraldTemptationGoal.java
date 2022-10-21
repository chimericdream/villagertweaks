package com.chimericdream.villagertweaks.goals;

import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.recipe.Ingredient;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class VTEmeraldTemptationGoal extends TemptGoal {
    public VTEmeraldTemptationGoal(PathAwareEntity entity) {
        super(
            entity,
            0.5D,
            Ingredient.fromTag(TagKey.of(Registry.ITEM_KEY, new Identifier("villagertweaks", "villager_temptation_items"))),
            false
        );
    }
}
