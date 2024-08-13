package com.chimericdream.villagertweaks.goals;

import com.chimericdream.villagertweaks.tag.VTTags;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.mob.PathAwareEntity;

public class VTEmeraldTemptationGoal extends TemptGoal {
    public VTEmeraldTemptationGoal(PathAwareEntity entity) {
        super(
            entity,
            0.5D,
            item -> item.isIn(VTTags.TEMPTATION_ITEMS),
            false
        );
    }
}
