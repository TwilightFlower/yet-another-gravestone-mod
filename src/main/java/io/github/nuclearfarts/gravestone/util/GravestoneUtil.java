package io.github.nuclearfarts.gravestone.util;

import java.util.ArrayList;
import java.util.List;

import io.github.nuclearfarts.gravestone.GravestoneBlockEntity;
import io.github.nuclearfarts.gravestone.GravestoneMod;
import io.github.nuclearfarts.gravestone.PlacementContextAccess;
import io.github.nuclearfarts.gravestone.mixin.BlockAccessor;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.ServerTask;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

public final class GravestoneUtil {
	private GravestoneUtil() {};
	
	public static void placeGrave(PlayerEntity player) {
		World world = player.world;
		if(!world.getGameRules().getBoolean(GameRules.KEEP_INVENTORY)) {
			List<ItemStack> items = new ArrayList<>();
			items.addAll(player.inventory.main);
			items.addAll(player.inventory.armor);
			items.addAll(player.inventory.offHand);
			player.inventory.main.clear();
			player.inventory.armor.clear();
			player.inventory.offHand.clear();
			GravestoneMod.runDropHandlers(player, items);
			BlockPos gravePos = findGravePos(world, new BlockPos(player.getPos()));
			world.getServer().send(new ServerTask(world.getServer().getTicks(), placeGraveRunnable(world, gravePos, items, player.totalExperience)));
		}
	}
	
	public static BlockPos findGravePos(World world, BlockPos player) {
		DimensionType dimension = world.dimension.getType();
		BlockPos playerPos = new BlockPos(player.getX(), clampY(dimension, player.getY()), player.getZ());
		if(canPlaceGrave(world, playerPos)) {
			return playerPos;
		}
		BlockPos.Mutable checkPos = new BlockPos.Mutable();
		for(int x = playerPos.getX() + 5; x >= playerPos.getX() - 5; x--) {
			checkPos.setX(x);
			for(int y = clampY(dimension, playerPos.getY()) + 5; y >= clampY(dimension, playerPos.getY()) - 5; y--) {
				checkPos.setY(y);
				for(int z = playerPos.getZ() + 5; z >= playerPos.getZ() - 5; z--) {
					checkPos.setZ(z);
					if(canPlaceGrave(world, checkPos)) {
						return drop(world, checkPos);
					}
				}
			}
		}
		checkPos.set(playerPos);
		checkPos.setY(clampY(dimension, playerPos.getY()));
		while(world.getBlockState(checkPos).getBlock() == Blocks.BEDROCK) {
			checkPos.setY(checkPos.getY() + 1);
		}
		return checkPos.toImmutable();
	}
	
	
	public static int clampY(DimensionType dimension, int y) {
		if(dimension == DimensionType.THE_NETHER && y < 127) { //don't spawn on nether ceiling, unless the player is already there.
			return MathHelper.clamp(y, 1, 126); //clamp to 1 -- don't spawn graves the layer right above the void, so players can actually recover their items.
		} else {
			return MathHelper.clamp(y, 1, 255);
		}
	}
	
	
	public static boolean canPlaceGrave(World world, BlockPos pos) {
		BlockState state = world.getBlockState(pos);
		try {
			return !(pos.getY() < 0 || pos.getY() > 255) && (state.isAir() || state.canReplace(new PlacementContextAccess(world, null, Hand.MAIN_HAND, ItemStack.EMPTY, new BlockHitResult(new Vec3d(pos.getX(), pos.getY(), pos.getZ()), Direction.UP, pos, true))) || !((BlockAccessor)state.getBlock()).getCollidable()); }
		catch(NullPointerException e) {
			 return false;
		 }
	}
	
	
	public static BlockPos drop(World world, BlockPos pos) {
		DimensionType dimension = world.dimension.getType();
		BlockPos.Mutable searchPos = new BlockPos.Mutable().set(pos);
		int i = 0;
		for(int y = pos.getY() - 1; y > 1 && i < 10; y--) {
			i++;
			searchPos.setY(clampY(dimension, y));
			if(!world.getBlockState(searchPos).isAir()) {
				searchPos.setY(clampY(dimension, y + 1));
				return searchPos.toImmutable();
			}
		}
		return pos;
	}
	
	public static Runnable placeGraveRunnable(World world, BlockPos pos, List<ItemStack> inv, int xp) {
		return () -> {
			world.setBlockState(pos, GravestoneMod.GRAVESTONE.getDefaultState());
			GravestoneBlockEntity be = (GravestoneBlockEntity) world.getBlockEntity(pos);
			be.inventory = inv;
			be.xp = xp;
			be.markDirty();
		};
	}
}
