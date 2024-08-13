package com.chimericdream.villagertweaks.item;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class BaggedVillagerItem extends Item {
    public BaggedVillagerItem(Settings settings) {
        super(settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        VillagerEntity villager = EntityType.VILLAGER.create(world);
        assert villager != null;

        try {
            NbtComponent component = user.getStackInHand(hand).get(DataComponentTypes.CUSTOM_DATA);
            assert component != null;
            NbtCompound nbt = component.copyNbt();

            villager.readCustomDataFromNbt(nbt);
            villager.refreshPositionAndAngles(user.getBlockPos(),0,0);

            world.spawnEntity(villager);

            user.getStackInHand(hand).decrement(1);
            user.setStackInHand(hand, new ItemStack(Items.BUNDLE));
        } catch (Exception e) {
            return TypedActionResult.fail(user.getStackInHand(hand));
        }

        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
