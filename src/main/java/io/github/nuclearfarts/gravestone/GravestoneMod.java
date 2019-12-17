package io.github.nuclearfarts.gravestone;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.ImmutableSet;

import io.github.nuclearfarts.gravestone.compat.GravestoneDropHandler;
import io.github.nuclearfarts.gravestone.compat.TrinketsGravestoneDropHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class GravestoneMod implements ModInitializer {
	
	public static final String MODID = "yet_another_gravestone_mod";
	public static final Logger LOGGER = LogManager.getLogger("YetAnotherGravestoneMod");
	public static final Block GRAVESTONE = new GravestoneBlock(Block.Settings.of(Material.PISTON).strength(0.5f, 1200));
	public static final BlockEntityType<GravestoneBlockEntity> GRAVESTONE_BLOCK_ENTITY = new BlockEntityType<GravestoneBlockEntity>(GravestoneBlockEntity::new, ImmutableSet.of(GRAVESTONE), null);
	
	private static final Collection<GravestoneDropHandler> COMPAT_HANDLERS = new ArrayList<>();
	
	@Override
	public void onInitialize() {
		Registry.register(Registry.BLOCK, id("gravestone"), GRAVESTONE);
		Registry.register(Registry.BLOCK_ENTITY, id("gravestone"), GRAVESTONE_BLOCK_ENTITY);
		
		addHandlerIfLoaded("trinkets", TrinketsGravestoneDropHandler::new);
	}
	
	public static Identifier id(String id) {
		return new Identifier(MODID, id);
	}
	
	private static void addHandlerIfLoaded(String modId, Supplier<GravestoneDropHandler> handlerSupplier) {
		if(FabricLoader.getInstance().isModLoaded(modId)) {
			LOGGER.info("Compatibility handler registered for " + modId);
			addDropHandler(handlerSupplier.get());
		}
	}
	
	public static void addDropHandler(GravestoneDropHandler handler) {
		COMPAT_HANDLERS.add(handler);
	}
	
	public static void runDropHandlers(PlayerEntity player, List<ItemStack> graveInv) {
		for(GravestoneDropHandler handler : COMPAT_HANDLERS) {
			handler.handle(player, graveInv);
		}
	}
	
	public static Runnable placeGraveRunnable(World world, BlockPos pos, List<ItemStack> inv) {
		return () -> {
			world.setBlockState(pos, GRAVESTONE.getDefaultState());
			GravestoneBlockEntity be = (GravestoneBlockEntity) world.getBlockEntity(pos);
			be.inventory = inv;
			be.markDirty();
		};
	}
}
