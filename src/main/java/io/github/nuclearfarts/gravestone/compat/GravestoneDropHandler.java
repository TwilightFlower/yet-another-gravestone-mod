package io.github.nuclearfarts.gravestone.compat;

import java.util.List;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

@FunctionalInterface
public interface GravestoneDropHandler {
	void handle(PlayerEntity player, List<ItemStack> gravestoneInventory);
}
