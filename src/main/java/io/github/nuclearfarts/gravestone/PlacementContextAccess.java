package io.github.nuclearfarts.gravestone;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;

public class PlacementContextAccess extends ItemPlacementContext {

	public PlacementContextAccess(World world, PlayerEntity player, Hand hand, ItemStack itemStack, BlockHitResult blockHitResult) {
		super(world, player, hand, itemStack, blockHitResult);
	}

}
