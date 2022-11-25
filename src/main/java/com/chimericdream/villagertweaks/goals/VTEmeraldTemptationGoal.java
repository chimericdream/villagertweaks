package com.chimericdream.villagertweaks.goals;

import com.chimericdream.villagertweaks.tag.VTTags;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.recipe.Ingredient;

public class VTEmeraldTemptationGoal extends TemptGoal {
    public VTEmeraldTemptationGoal(PathAwareEntity entity) {
        super(
            entity,
            0.5D,
            Ingredient.fromTag(VTTags.TEMPTATION_ITEMS),
            false
        );
    }
}
